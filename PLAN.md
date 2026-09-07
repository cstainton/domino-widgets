# Domino Widgets: shared-source GWT and TeaVM plan

Status: implementation in progress, 7 September 2026. Current evidence and remaining limitations are linked below.

## Decision and intended outcome

Create `domino-widgets` as a standalone Maven library project, following Bootstrap Widgets' shared-source approach. Applications select a GWT or TeaVM backend while using the original Domino widget packages and behaviour. Neither backend depends on Verrai, Sarto, CDI or a particular application framework.

Use the corrected [cstainton/domino-ui fork](https://github.com/cstainton/domino-ui) as the source of truth for widget code. Keep general correctness fixes there; keep compiler compatibility machinery in independently reusable artifacts. The current local fork includes commit `08c1bab` (Address SpotBugs findings); verify its remote availability and record the full commit before establishing the first reproducible build.

Retire the separate `verrai-widgets-domino` and `verrai-widgets-bootstrap` implementations after migrating their consumers. Framework integration, where necessary, belongs in optional thin adapters owned by the consuming framework. Those adapters must not recreate widget rendering, state or interaction logic.

The initial supported targets are browser JavaScript produced by modern GWT and TeaVM. WebAssembly GC can be evaluated later and is not part of initial parity. This project establishes reusable compatibility building blocks; it does not promise that arbitrary GWT libraries will immediately compile on TeaVM.

## Architecture and proposed artifacts

Artifact names below are proposed. Use a publishing namespace controlled by this project, rather than publishing replacement binaries under upstream's coordinates. Preserve original Java package names for source compatibility.

| Artifact / area | Responsibility |
|---|---|
| `domino-widgets-parent` / `domino-widgets-bom` | Reactor configuration and aligned backend, compatibility and asset versions |
| Pinned source input | Original fork sources, shared sources, generated inputs and resources, with commit and checksum recorded |
| `domino-widgets-gwt` | GWT library packaging, module descriptors and original Elemental2/JsInterop dependencies |
| `domino-widgets-teavm` | Compile the same widget sources against TeaVM bindings and explicit compiler seam replacements |
| `domino-widgets-assets` | Version-matched CSS, fonts, icons and other browser assets, without duplicate embedding |
| `teavm-elemental2-compat` | Independently consumable Elemental2-compatible browser bindings implemented with TeaVM JSO |
| `teavm-jsinterop-compat` | Supported JsInterop base operations and any required compiler integration; annotations alone are insufficient |
| Modular GWT service compatibility | Reuse/adapt required timer, events, formatting, validation and related APIs; establish ownership after dependency inventory |
| Shared contracts and two browser fixtures | Same scenarios and assertions against both compiled backends |
| Two showcase launchers | One shared example screen/source set, separate GWT and TeaVM bootstraps |

Consumers choose exactly one backend in an application. Both contain the same widget package names; original and replacement Elemental2 implementations must not coexist on the TeaVM classpath. Add dependency convergence and duplicate-class checks, with narrowly documented treatment of intentional source inputs.

The GWT path should retain native GWT/Elemental2 dependencies wherever they already work. It does not need an artificial emulation layer. The TeaVM path supplies equivalent browser-facing contracts. Use the existing Bootstrap compatibility artifacts where contracts match; avoid a second competing implementation of the same GWT APIs. If those artifacts need extraction to a neutral project, preserve compatibility for Bootstrap consumers during that move.

## Source intake and upstream maintenance

1. Establish a modern-GWT baseline of the corrected fork before adding compatibility changes. Capture its dependency graph, generated sources and resources, and verify existing fixes are included.
2. Prefer immutable source-classifier artifacts produced from the pinned fork revision. If source publication is initially unavailable, use a deterministic archive intake with a recorded commit and checksum. Do not fetch a moving branch during ordinary builds.
3. Feed the same source input into both backend builds. Keep imports and public `org.dominokit.domino.ui` API intact wherever possible. Include `domino-ui-shared`, annotation processor outputs, GWT descriptors and assets in the inventory.
4. Maintain a small, explicit manifest of excluded/replaced classes or deterministic transformations, each with a reason and regression test. Fail when an expected upstream input changes. Generated compatibility code must be reproducible and never hand-edited.
5. Record fork revision, original upstream baseline, patch list and dependency versions in release metadata. Preserve upstream licence and notice files in derived distributions.
6. Update the pinned source through a reviewable change that regenerates bindings, reports API drift, and runs both browser suites. General widget fixes continue to flow through the fork and can be proposed upstream separately.

## Phase 1 — inventory and establish the compiler baseline

- Inventory actual referenced members and transitive dependencies, not just imports: Elemental2 DOM/core/SVG/storage, JsInterop annotations/base, Domino native declarations, modular `org.gwtproject` APIs, formatting/validation, history, REST and generated code.
- Classify each dependency as shared Java, original GWT dependency, existing TeaVM equivalent, missing bridge or optional feature. The absence of `com.google.gwt` imports does not imply the absence of modular GWT dependencies.
- Start GWT validation at 2.13.1, listed on the [official GWT versions page](https://www.gwtproject.org/versions.html), and pin the tested compiler/JDK combination. The fork currently uses GWT 2.12.1.
- Evaluate the TeaVM version used by Bootstrap Widgets (currently 0.15.0) as the common baseline; the Domino fork currently pins 0.10.2. Confirm required JSO features in the selected release rather than assuming current documentation applies to an older compiler.
- Produce a dependency/compatibility matrix with explicit unsupported areas, plus a real GWT fixture using the pinned sources.

Exit: reproducible GWT browser build; all source/assets accounted for; bounded compatibility backlog and chosen compiler/JDK versions recorded.

## Phase 2 — prove the reusable browser binding layer

Use generated same-package TeaVM bindings where feasible. Validate property reads/writes, native constructors, globals, inheritance, arrays, generics and union types, callbacks, null/undefined, casts, property maps and Java/JavaScript value conversion. Test callback identity and removal, including across repeated attachment.

[TeaVM JSO documentation](https://teavm.org/docs/runtime/jso.html) describes `JSObject`, properties, constructors and public fields on `@JSClass` types. These are promising mechanisms, not proof that original JsInterop declarations are directly compatible. Prototype difficult binding cases first. For unsupported declaration shapes, compare a small compiler extension with deterministic source transformation; record the chosen mechanism and its maintenance cost before broad generation.

Build one shared original-Domino screen containing:

- A button with registered and removed handlers.
- A validated input with value-change events.
- A dialog with its original close, focus and keyboard behaviour.
- A datatable with selection, filtering and row updates.

Run the same scenarios in GWT and TeaVM, including repeated attach/detach. Compare actual behaviour with the original GWT baseline; matching a baseline is not an accessibility certification. Browser tests must invoke real reachable widgets: the existing empty Domino TeaVM entry point provides no such evidence.

Exit: this screen works in both backends with shared widget source, standalone dependencies and a documented, bounded set of compatibility seams. If pervasive widget rewrites are required, revisit the binding design before expanding scope.

## Phase 3 — expand widget and dependency coverage

Expand in tested batches, keeping a public feature matrix with states: unassessed, compiles, browser-tested, unsupported.

1. Core elements, layout, styles, events, lifecycle, basic controls and navigation.
2. Forms, selection, validation, date/time and locale-sensitive formatting.
3. Datatable plugins, trees, menus, dialogs and application layout.
4. Uploads, rich text, advanced components and optional history/REST integrations.

Keep optional integrations separately selectable where the source dependency graph permits. Reuse the corrected history/REST implementations only after testing their contracts with the new backend. Verify icons/processors and asset loading on both targets. Do not silently omit an unsupported feature from an artifact advertised as complete.

Exit: all existing Verrai widget use cases have a tested replacement or an explicit migration blocker. Broader original Domino coverage continues to be reported separately from that migration milestone.

## Phase 4 — migrate applications and retire Verrai widget implementations

- Inventory imports and behavioural assumptions in Verrai/Sarto applications, templates and generated code. Create a mapping from each old widget API to the original Domino or Bootstrap API; these are not necessarily drop-in replacements.
- Migrate one representative application first, including dependency injection, value binding, validation and template attachment. Use optional framework adapters only where required. Prove the standalone showcase needs none of them.
- Move Bootstrap consumers to `bootstrap-widgets` and Domino consumers to `domino-widgets`, then run application regressions.
- Deprecate the old modules during migration, freeze new feature development in them, and remove them after all known consumers have moved. Record any public API break and migration instructions in the relevant release.

Exit: no active application depends on duplicated Verrai widget implementations; framework integration remains outside the standalone widget and compatibility dependencies.

## Phase 5 — release and demonstrate reuse

Publish backend libraries, source/Javadoc artifacts, assets, compatibility artifacts and an aligned BOM. Document minimal GWT and TeaVM applications, dependency exclusions, compiler versions, feature coverage and limitations. Test consumption from a clean external sample, without sibling checkouts or unpublished local artifacts.

Use a small second Elemental2-based library as a reuse test: it must consume the compatibility artifacts without depending on Domino or Verrai. Choose that library after the inventory reveals overlap. This checks whether the bridge is actually general-purpose. Libraries using deferred binding, JSNI, generators or other GWT facilities will still need separate support; they are not covered merely by an Elemental2 bridge.

## Verification and reporting

- Run JVM tests for ordinary Java logic and browser contract tests for interop/widget behaviour. Stubs that bypass browser APIs are insufficient evidence of backend compatibility.
- Run both compiler builds and shared browser contracts in CI; include Chromium initially and Firefox/WebKit before declaring broad browser support. Keep deterministic fixture data and capture failures with browser logs/screenshots.
- Check lifecycle cleanup, event ordering, rendering/assets, locale/time behaviour and the existing correctness regressions. Exercise optimized production builds as well as development fixtures.
- Generate SpotBugs reports on each normal build, local to each reactor/module, named `<artifactId>-spotbugs.html`, `.xml` and `.sarif` under `target/spotbugs/`, with a reactor index. Surface analyzer failures and missing classes separately from findings; require new high-priority findings to be resolved or specifically triaged for release.
- Publish browser results, feature coverage and reproducibility metadata alongside each release. Measure representative bundle size and startup only once both implementations execute equivalent functionality.

## First implementation tranche

Create the reactor and pinned-source intake; establish the GWT fixture; prototype the hardest Elemental2/JsInterop seams; implement the shared four-component screen; run its contracts on both backends. Deliver the compatibility inventory, explicit exclusions and actual test reports with that tranche. Estimate remaining coverage from those results rather than from a successful empty compilation.

Background: [source and functionality comparison](docs/WIDGET-BACKEND-COMPARISON.md).

## Implementation status — September 2026

The standalone reactor and reusable binding layer are implemented. Coverage now includes 51 original gallery pages, 167 sample methods, targeted interactions and three browser engines on both compiler backends. See [README](README.md), [coverage](docs/COMPATIBILITY.md), [showcase inventory](docs/SHOWCASE.md), and [verification reports](reports/). Independent Maven-consumer applications and publication verification are provided under `examples/`. A representative Sarto migration is prepared, with unchanged-baseline framework blockers documented in [MIGRATION.md](docs/MIGRATION.md). Full application retirement and all-widget behavioral parity remain open.
