# Port design

Domino Widgets adds TeaVM support to DominoKit’s Domino UI by adapting the browser
and compiler boundaries used by its widgets. GWT and TeaVM compile the same pinned
widget implementations, with compiler-specific bindings beneath them.

The widget API, rendering and behaviour come from DominoKit. This repository owns
the compatibility modules, deterministic source adaptations, packaging and shared
verification. It also publishes a GWT build to make the same source revision usable
and testable through both compilers.

## Why retain a GWT build

The original scope includes adoption through either compiler. A GWT build from the
same source revision also supplies a reference for checking widget behaviour against
TeaVM. Publishing it lets consumers use that matched revision; publication is a
distribution choice, not a technical requirement for the TeaVM port. The GWT build
could remain internal to verification if the public distribution becomes TeaVM-only.

## Keep the widget implementation shared

The source archive comes from the corrected `cstainton/domino-ui` fork. It contains
DominoKit's widget sources plus the fork's service adaptations and general fixes;
it is not an unmodified snapshot of current upstream. Original Java package names
and copyright headers are preserved.

The build reads the archive, verifies its checksum and produces generated sources
under `target/`. The [seam manifest](SEAMS.json) records the bounded adaptations.
General widget fixes belong in the source fork so both compiler builds receive them.

## Put compatibility below the widgets

On GWT, the widgets use native Elemental2 and JsInterop. On TeaVM, generated
same-package bindings connect those calls to TeaVM's browser interop. The Java
widget code continues to refer to the same types.

Modular editor, SafeHtml and i18n services are packaged separately. GWT supplies
its compiler-specific service implementations; TeaVM uses adapted native declarations.
The matched CSS, fonts and icons are shared by both builds through one assets JAR.

## Modules



| Artifact | Responsibility |
|---|---|
| `domino-widgets-gwt` | Original widgets with native Elemental2/JsInterop |
| `domino-widgets-teavm` | Same widgets with generated native declaration adaptations |
| `teavm-elemental2-compat` | Generated same-package Elemental2 browser bindings |
| `teavm-jsinterop-compat` | Explicit native/Java value conversions, maps, arrays and casts |
| `gwt-modular-services` | Fork's modular editor, SafeHtml and i18n APIs, with native GWT compiler seams |
| `teavm-gwt-modular-services` | Same fork service APIs with generated TeaVM native declarations |
| `domino-widgets-assets` | Matched CSS, fonts and icon resources |
| `binding-generator` | Deterministic JavaParser-based build tool; not a runtime widget dependency |

## Derive the showcase from the original examples

The showcase is adapted from `DominoKit/domino-ui-demo`, pinned at
`51e1f75d43179a544c010ca5e88517c93263eeed` on its `version-2` branch.
It includes 51 original pages and 167 sample methods. Their provenance and
individual transformations are recorded in the [showcase guide](SHOWCASE.md)
and [showcase lock](../upstream/showcase-lock.json).

The original application shell, asynchronous view loading and generated code-resource
presentation are replaced by a small shared Java launcher. Selected sample bodies,
helper models and assets are retained or explicitly adapted. This is a derived
showcase covering the included examples; it does not reproduce the entire upstream
demo application.

Both compilers run that shared gallery. Browser contracts exercise rendering and
selected interactions, while separate binding contracts check the compatibility
boundaries. The [coverage guide](COMPATIBILITY.md) distinguishes tested behaviour
from declarations that merely compile.

The gallery home, searchable navigation and example descriptions belong to the port's
shared launcher. Original samples keep their widget implementations and styles;
each gallery page links to its pinned Java adapter and the corresponding live upstream
example. Lifecycle and browser API fixtures remain on explicit development routes.

`upstream/showcase-lock.json` records the retained example files, supporting models,
assets and SHA-256 hashes. `scripts/generate-showcase.py --check` checks that the
extracted adapters still match the pinned sources. The adapters replace MVP views,
generated source-display resources, JSON annotation processors and remote demo
services. Contact tables use deterministic sample records; country JSON is decoded
through Elemental2, and images use pinned local assets. Upload tests exercise the
original XHR/FormData implementation with a local server response.

The original demo repository did not include a root license file at the selected
commit. Extracted examples retain attribution; the widget library's Apache-2.0
license does not newly license upstream demo material.

## Make source updates reproducible


[Source lock](../upstream/source-lock.json) records the corrected fork commit and archive SHA-256. [Binding locks](../upstream/bindings-lock.json) pin the published Elemental2 inputs. Every build checks these hashes before generating sources; there are no moving-branch downloads. Generated files live under `target/` and must not be edited.

The complete source archive retains the original sources, checked-in generated icons, processor sources, descriptors, resources and notices. `target/intake/inventory.json` accounts for every archived file. [Seam manifest](../docs/SEAMS.json) describes the bounded adaptations; [member inventory](../reports/member-inventory.json) records binary field/method references rather than imports alone.

Apache-2.0; upstream copyright headers and notices are preserved. Font/icon assets retain their upstream provenance; see [asset inventory](../docs/ASSETS.md).

Updating upstream means changing the pinned source deliberately, reviewing the fork
changes, regenerating bindings and assets, and rerunning both compiler suites.
The [upstream assessment](UPSTREAM.md) records the currently outstanding changes.
Build commands are in the [development guide](DEVELOPMENT.md).
