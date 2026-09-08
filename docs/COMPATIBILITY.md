# Coverage and limitations

A compiled declaration is not evidence of browser behavior. `browser-tested` below refers only to the named scenarios. The shared suite runs against TeaVM in Chromium, Firefox and WebKit, for development and optimized production builds. Exact outcomes and compiler/source metadata are recorded under [reports](../reports).

| Area | Coverage |
|---|---|
| Original gallery | 51 pages, 167 extracted sample methods: render, nonempty content and uncaught browser errors; [page inventory](SHOWCASE.md) |
| Showcase scrolling | Native wheel scrolling in Chromium, Firefox and WebKit; native touch scrolling to the footer and back in Chromium, on both compiler builds. Physical iOS touch behaviour remains unverified. |
| Button and lifecycle | Clicks, exact handler removal, repeated detach/reattach, mutation observers, original sample interactions |
| Text inputs | Required validation, invalid-state clearing, value-change events, clearing and changed numeric input |
| Dialogs | Repeated open/close, Escape, original message/alert/custom content interactions |
| Tables | Original contact tables and 11 plugin pages render; contracts cover selection, record replacement, store search and pagination |
| Tree, chips, tabs | Nested branch expansion/collapse; removing chips; repeated visible tab switching |
| Calendar | Day selection, next/previous month navigation; strict leap-date parsing and invalid-date rejection |
| Locale labels | Explicit Spanish and Arabic month labels through native Intl; full CLDR/week-start/time-zone parity remains unassessed |
| Advanced forms | Original dynamic country suggestions fetch JSON and select a result; original file upload sends multipart bytes through XHR and handles a controlled successful response |
| Rich text | Original editor renders, accepts content editing, returns HTML and resets its value; toolbar commands, clipboard and browser permission cases remain unassessed |
| Browser APIs | Local/session storage roundtrip/deletion, SVG namespace, Blob URL/fetch/text, FileReader, promise fulfillment/rejection conversion, pushState/popstate |
| Elemental2 and JsInterop foundations | Inherited fields, globals/native constructors, generic arrays/maps, native and Java identity, primitive conversion, missing values, null/undefined, custom events, union timer callbacks |
| Independent reuse | Published Elemental2 console logger uses compatibility artifacts without Domino or Verrai |
| Remaining widget behaviors | `compiles`; a gallery render test does not establish all interactions, accessibility or edge cases |
| Native constructor varargs with supplied arguments | `unsupported`; TeaVM 0.15 does not spread them. Zero-argument Array is explicitly adapted |
| `Js.asConstructorFn(Class)` | `unsupported`; explicit failure rather than an invented reflection mapping |
| Arbitrary JsInterop reflection/base surface | Outside the implemented subset |
| Optional Domino history/REST packages | Not included; native API contracts above do not certify those optional libraries |
| WebAssembly GC | Outside the supported browser-JavaScript targets |
| Verrai/Sarto application retirement | In progress; [consumer inventory and concrete blocker](MIGRATION.md) |

Baseline: TeaVM 0.15.0, JDK 21, Java release 17, Elemental2 1.2.3, native JsInterop base 1.0.1 and annotations 2.0.2. Historical reports retain the former GWT baseline; current builds use upstream links for comparison.

The complete widget archive is retained. Original widget implementations are shared; general fixes live in the source fork. The framework demo shell, optional history/REST modules and annotation/webjar processor build machinery are excluded from the runtime libraries. Checked-in generated icons and their inputs are retained.

Known upstream behavior: rapid tree collapse during the expansion animation can leave height inconsistent with the collapsed flag (also reproduced on GWT); the normal expand/collapse contract waits for the animation to finish.  `DataTable.filterRows` adds `table-row-filtered`, but the pinned stylesheet has no corresponding hiding rule. Tests use actual data-store search filtering. GitHub Pages has no upload backend, and gallery media rendering does not certify playback or device APIs. Remaining advanced table combinations, full application layout, locale/time-zone edge cases, upload cancellation/error/retry, rich-text toolbar/clipboard and optional integrations need further targeted contracts.
