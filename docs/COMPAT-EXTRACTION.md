# Compatibility extraction

The implementation now lives in the standalone
[cstainton/teavm-compat](https://github.com/cstainton/teavm-compat) repository. Its
independent parent and BOM manage these runtime artifacts under `io.instanto`:

| Former artifact | New artifact |
|---|---|
| `teavm-jsinterop-compat` | `jsinterop-base-compat` |
| `teavm-elemental2-compat` | `elemental2-compat` |
| `teavm-gwt-modular-services` | `gwt-modular-services-compat` |
| Bootstrap's `teavm-gwt-compat` | `gwt-user-compat` |

Elemental2 includes core, DOM, promises, SVG and web storage at the pinned version.
Modular services cover the current editor, i18n and SafeHtml subset. These names do
not imply full upstream API coverage.

The shared generator accepts explicit inputs and outputs. Immutable compatibility
inputs, checksums, original notices and contracts live in the new repository;
Domino retains its widget source archive and widget-specific input selection.
Runtime JARs do not depend on the generator or JavaParser.

Bootstrap's duplicate JsInterop annotation declarations were replaced by the
official dependency. A mixed browser contract exercises both GWT client and Elemental2
against the same document. Old runtime coordinates are relocation POMs, preserving
migration metadata without duplicate Java implementations.

The final distribution scope is TeaVM-only for Domino. Its former GWT artifacts and
showcase have been removed; comparison links point to DominoKit upstream. Bootstrap
continues to consume the shared contracts for its native GWT reference tests.

See the new repository's [design](https://github.com/cstainton/teavm-compat/blob/main/docs/DESIGN.md)
and [migration guide](https://github.com/cstainton/teavm-compat/blob/main/docs/MIGRATION.md).
