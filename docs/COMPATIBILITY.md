# Coverage and limitations

Coverage states are `unassessed`, `compiles`, `browser-tested`, and `unsupported`. A successful Java or TeaVM compile is not proof of browser behavior.

| Area | Status / evidence |
|---|---|
| Original Button | Browser contracts: clicks, exact handler removal, repeated detach/reattach |
| Original TextBox | Browser contracts: required validation, clearing invalid state, value-change event |
| Original Dialog | Browser contracts: repeated open/close and Escape; original implementation retained |
| Original DataTable | Browser contracts: real rendering, selection, local store search, replacing records |
| Elemental2 properties/globals | Browser contracts: inherited field access, document, native event/Date constructors |
| Callbacks | Browser contracts: registration/removal with capture, timer callback through a union overload |
| JsInterop maps/arrays | Browser contracts: generic string arrays, Java object identity, primitive conversion, missing values, null/undefined, custom-event detail |
| Original showcase samples | Browser-tested basic button, form and message-dialog interactions; see SHOWCASE.md |
| Other original widget sources | `compiles`; not advertised as browser-tested |
| Other Elemental2 APIs (SVG, uploads, media, storage, promises, rich text) | `compiles` declarations; runtime coverage remains unassessed |
| Fork i18n services | Basic Intl operations execute through core widgets; broad locale/time/calendar behavior remains unassessed |
| Native constructor varargs with supplied arguments | `unsupported`; TeaVM 0.15 does not spread them. The zero-argument Array case is explicitly adapted |
| `Js.asConstructorFn(Class)` | `unsupported`; fails explicitly rather than inventing a constructor mapping |
| Java reflection/Class conversion and arbitrary JsInterop base APIs | Outside the implemented surface; this is not a complete JsInterop replacement |
| WebAssembly GC | Outside the initial supported targets |
| Optional Domino history and REST | Existing ports inventoried, not included in the widget artifacts or declared validated here |
| Full Verrai/Sarto migration | Blocked on the wider consumer widget coverage in MIGRATION.md |

Compiler baselines: GWT 2.13.1, TeaVM 0.15.0, JDK 21, Java source/release level 17, Elemental2 1.2.3, native JsInterop base 1.0.1 and annotations 2.0.2. The native GWT fixture runs the strict compiler, so unreferenced source errors are not silently ignored.

The full archive is retained, but demo applications, history/REST modules, annotation processor build machinery and webjar build machinery are not compiled into the widget libraries. Checked-in generated icon classes and their resource inputs are retained. No widget class is replaced by a reimplementation.

Known upstream behavior: `DataTable.filterRows` adds `table-row-filtered`, but the pinned stylesheet has no corresponding hiding rule. The fixture tests actual data-store search filtering. This finding does not disappear merely because both backends behave alike.

Do not infer full accessibility certification, all-browser support or all-widget parity from this tranche. The reports identify the exact browser scenarios run.
