# Bootstrap Widgets, Verrai façades and Domino UI

Source/API comparison on 7 September 2026. No migration or full browser parity run was performed. Counts describe Java source files, not widget counts or quality scores.

## Conclusion

Use the newer Bootstrap Widgets shared-source architecture as the preferred approach. A Domino equivalent is feasible in principle, but needs an Elemental2/JsInterop compatibility boundary, not simply the existing GWT compatibility JAR. Start with a representative original Domino widget slice before committing to broad coverage.

Keep Verrai's binding/injection interfaces as thin adapters over the selected widget backend. Avoid maintaining a second implementation of each widget's rendering and interaction behaviour.

## What currently exists

| Component | Implementation | Implication |
|---|---|---|
| `bootstrap-widgets` | GWT widget sources are packaged as source artifacts, consumed by TeaVM modules, and combined with GWT compatibility classes plus native bridges | Shared behaviour and API across compiler backends; remaining seams and exclusions are explicit |
| `verrai-widgets-domino` | 53 Java files; own TeaVM DOM code with `dui-*` CSS; no dependency on the original Domino UI widget library | Selected Domino-styled widgets, not a compatibility backend for the original widget implementation |
| `verrai-widgets-bootstrap` | 32 Java files; own TeaVM DOM code and Bootstrap styling; no dependency on the new Bootstrap Widgets modules | This older module also duplicates behaviour; it does not automatically gain the new backend's features |
| `domino-ui/domino-ui` | 1,108 Java files, with Elemental2 references in 552 files and JsInterop references in 59 | The original widget implementation remains available, but its browser binding surface needs a proven TeaVM bridge |

The new Bootstrap 5 core has 400 Java files and the shared TeaVM GWT compatibility layer has 298. These include support types and are not directly comparable widget counts. The Domino import inventory contains 194 distinct Elemental2/JsInterop import expressions, including wildcard/static imports; it is a scoping aid, not a complete API inventory.

[Bootstrap shared-source build](/Users/carls/IdeaProjects/bootstrap-widgets/teavm/teavm-bootstrap5/pom.xml) · [Verrai Domino description/dependencies](/Users/carls/IdeaProjects/verrai/verrai-widgets-domino/pom.xml) · [Verrai Bootstrap dependencies](/Users/carls/IdeaProjects/verrai/verrai-widgets-bootstrap/pom.xml) · [Domino interop inventory](domino-interop-inventory.json)

## Functional comparison

“Original Domino” describes source features, not features proven usable in the present TeaVM port.

| Area | Current Verrai Domino | New Bootstrap Widgets, especially Bootstrap 5 | Original Domino UI |
|---|---|---|---|
| Basic UI | Buttons, alerts, cards, inputs, switches, navigation and layout wrappers | Broad shared widget set, Bootstrap 3 and 5 tracks | Broad fluent Java component set |
| Forms and selection | Basic inputs and native select; individual Verrai value interfaces | Validators, suggest/select controls; SearchableSelect supports multiple selection, tags and async option queries | Rich forms/select controls, validation and data integration |
| Tables | String/widget rows, local sorting/filtering/pagination; string sorting is case-insensitive lexical comparison | Tabulator integration exposes editable cells, grouping, tree rows, filters, pagination and remote loading | Extensive datatable plugin model: selection, markers, row details, summaries, header filters, drag/drop and more |
| Dialogs and feedback | Own DOM/backdrop show/hide implementation; no keyboard/focus-trap implementation seen in Modal | Bootstrap component/event bridges; modal lifecycle handlers, toast and offcanvas components | Dialog, notification, popover and loader implementations |
| Calendar and dates | A calendar implementation with navigation/selection and month/year helpers; more than a CSS-only wrapper | Datepicker bridge/source exists; do not assume parity with every calendar/time feature | Calendar/date and time-picker areas |
| Advanced components | Selected controls; no equivalents located for the full advanced Domino catalogue | Sortable lists, dashboard tiles/layout, image gallery; additional editor/date extras vary by backend | Trees, file upload, rich text, split panels, steppers and application layout |
| Styling | Host loads Domino CSS; matching classes does not ensure original markup or behaviour parity | Bootstrap-version-specific markup, resources and packaged dependency loading | Original Domino theme/style system |
| Verrai integration | Direct existing Verrai interfaces, CDI and template processing | Needs adapters to the Verrai widget/value/binding interfaces | Would also need a Verrai adapter layer |
| Evidence | No dedicated test trees found in the two Verrai widget façade modules; application tests may exercise them elsewhere | Browser contract/feature test sources exist; targeted tests passed in this work, but a broader Cucumber build hit undefined steps | Current empty TeaVM entry point does not establish widget execution coverage |

[Verrai table implementation](/Users/carls/IdeaProjects/verrai/verrai-widgets-domino/src/main/java/io/instanto/verrai/domino/TableWidget.java) · [Verrai modal](/Users/carls/IdeaProjects/verrai/verrai-widgets-domino/src/main/java/io/instanto/verrai/domino/Modal.java) · [Bootstrap integration capabilities and limitations](/Users/carls/IdeaProjects/bootstrap-widgets/BOOTSTRAP5-INTEGRATIONS.md) · [Bootstrap native feedback components](/Users/carls/IdeaProjects/bootstrap-widgets/BOOTSTRAP5-NATIVE-COMPONENTS.md)

## What a Domino compatibility backend needs

1. Compile a pinned original Domino source artifact against a TeaVM-compatible browser binding artifact. Preserve widget source and public package names where possible.
2. Generate or adapt the used `elemental2.dom`, `elemental2.core` and `jsinterop.base` surface: DOM types, properties, native constructors, arrays, callbacks, property maps, casts and browser globals. Also address Domino's own native JsInterop declarations.
3. Isolate exceptional compiler seams in small replacement classes or reproducible build-time transformations. Do not scatter handwritten TeaVM branches across hundreds of widgets.
4. Keep CSS/assets and shared Domino dependencies version-aligned. The history and REST TeaVM modules already exist separately and need integration tests, not silent replacement.
5. Add thin Verrai adapters, then migrate existing applications incrementally.

Elemental2 generates JsTypes for the GWT/J2CL JsInterop model. TeaVM uses its JSO model. Current TeaVM documentation includes public fields on `@JSClass` types, which makes field-style bindings a promising route; it does not mean arbitrary Elemental2 bytecode works unchanged. The local Domino POM pins TeaVM 0.10.2, while Bootstrap Widgets uses 0.15.0. Select and test a common version explicitly before relying on newer interop features. [Elemental2 project](https://github.com/google/elemental2) · [TeaVM JavaScript interop](https://teavm.org/docs/runtime/jso.html).

The existing Bootstrap shim helps with GWT APIs. The inspected Domino core has no `com.google.gwt` references and instead depends heavily on Elemental2, so substituting `teavm-gwt-compat` alone will not solve it. Reuse the build/testing pattern, rather than expecting the same shim to cover the same surface.

## Important evidence gaps

- Domino's `TeaVMEntryPoint.main()` is empty. Tree shaking can discard the widget code, so that smoke build is not a compatibility test. A useful entry point must instantiate and interact with real widgets. [Entry point](/Users/carls/IdeaProjects/domino-ui/domino-ui/src/main/java/org/dominokit/domino/ui/teavm/TeaVMEntryPoint.java).
- Some Bootstrap architecture notes/POM comments predate the shared-source and advanced-integration work. The current source-artifact dependencies and replacement bridges are stronger evidence than statements that TeaVM widgets never use UiBinder or that only Markdown has been ported.
- Bootstrap's TeaVM POMs still have backend-specific exclusions and replacements. Source sharing reduces divergence; it does not prove complete GWT behaviour, visual, accessibility or plugin parity.
- No bundle-size, speed or memory comparison was measured. Feature presence is not a performance or accessibility certification.

## Suggested next step

Make Bootstrap Widgets the default backend for new Bootstrap-style screens and adapt existing Verrai Bootstrap components over it progressively. Keep the current Domino façade stable while testing a source-sharing Domino backend.

Use one original Domino screen containing a button, validated input, dialog and datatable with selection/filtering. Exercise real clicks, focus, keyboard handling, callbacks, row updates, and repeated attach/detach. Run the same behaviour checks against the original GWT implementation and TeaVM. This reaches enough DOM/property/callback/lifecycle surface to judge feasibility; an empty build or button-only demo does not.

If that slice works with a bounded generated binding layer and few widget-source edits, extend it. If it requires pervasive widget rewrites, use Bootstrap as the main supported stack and retain only the Domino-specific features applications actually need. This is an architectural recommendation, not an approved migration or implementation estimate.
