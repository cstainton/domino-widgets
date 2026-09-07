# Prior TeaVM work and what is reused

Inspected the local corrected `cstainton/domino-ui` fork and GitHub branches `claude/port-to-teavm-fYebf`, `claude/teavm-phase-2`, `claude/teavm-demo-and-history-port`, and `teavm-timer-poc-7561862648147766461`.

* The merged fork contains `domino-history-teavm` and `domino-rest-teavm`. Their mappings identify reusable browser boundaries: history state property objects, pushState/popstate, custom events, XMLHttpRequest, FormData/Blob, timers and regular expressions.
* The working Geo Todo demo uses TeaVM DOM APIs directly. It does not demonstrate that original Domino widget implementations execute through Elemental2.
* The widget module's existing `TeaVMEntryPoint.main()` is empty. Its successful tree-shaken compilation is not widget compatibility evidence.
* The timer/i18n proof of concept identifies the timer and formatting seams. The pinned corrected fork already contains editor, SafeHtml, Intl-backed i18n APIs and Elemental2 timers. Those service implementations are reused in separate artifacts here. GWT uses native compiler services for the fork's unsupported `java.text.SimpleDateFormat` and URL encoding calls.
* `verrai-widgets-domino` supplies its own TeaVM DOM widgets and calendar adaptations. Its porting notes describe direct callbacks replacing custom events, simplified locale formatting and simplified lifecycle behavior. These are useful migration clues, but copying those widgets would recreate the duplicated implementation the plan aims to retire.
* TeaVM's SLF4J substitution needs its runtime classes on the application classpath as well as compiler discovery. Using only the plugin dependency from the old port caused a compiler substitution failure in the real fixture.

The compatibility work therefore follows the existing port's browser/service boundaries while retaining the original widget rendering, state, event and lifecycle code. General fixes remain in the separate source-fork branch: integral epoch milliseconds in `DominoId` prevent invalid CSS selectors, and an optional explicit locale in `BrowserDateTimeFormatInfo` enables tested native Intl month labels. Expanded browser contracts now exercise the old port's storage, promise, file, SVG and history boundary clues without copying its widget implementations.

References: [original TeaVM branch](https://github.com/cstainton/domino-ui/tree/claude/port-to-teavm-fYebf), [timer/i18n prototype](https://github.com/cstainton/domino-ui/tree/teavm-timer-poc-7561862648147766461), [merged demo work](https://github.com/cstainton/domino-ui/pull/6).
