# Separate TeaVM compatibility build

Proposed home: a standalone `cstainton/teavm-compat` repository. It would build and
publish reusable compatibility artifacts independently of the widget libraries.
Domino Widgets and Bootstrap Widgets would consume its published artifacts and
import its BOM, without reaching into a sibling checkout.

## Name the API being adapted

Use the upstream library name followed by `-compat`, under our existing `io.instanto`
groupId. Artifact descriptions and the README should state that these are independent
TeaVM adaptations and describe the supported API subset.

| Current artifact | Proposed artifact | API |
|---|---|---|
| `teavm-jsinterop-compat` | `jsinterop-base-compat` | `jsinterop.base.*` |
| `teavm-elemental2-compat` | `elemental2-compat` | Elemental2 core, DOM and promises |
| `teavm-gwt-modular-services` | `gwt-modular-services-compat` | The current `org.gwtproject` editor, i18n and SafeHtml subset |
| Bootstrap's `teavm-gwt-compat` | `gwt-user-compat` | The supported `com.google.gwt.*` client API subset |

`gwt-modular-services` is our grouping of several modular APIs, not an upstream
artifact name. Preserve that distinction in its description; it can later be split
into editor, i18n and SafeHtml artifacts if independent consumers need them.
`elemental2-compat` currently combines several upstream modules, so its documentation
must name those modules rather than imply coverage of every Elemental2 binding.

The aggregator would use `teavm-compat` as its parent artifact, with
`teavm-compat-bom` managing compatible versions. Its version would be independent
of either widget set's version.

## What belongs in the repository

- The reusable JsInterop base, Elemental2 and GWT compatibility implementations.
- Their immutable source inputs, notices, explicit adaptation rules and checksums.
- A general binding generator with explicit input and output arguments.
- Compatibility contracts and a small browser app proving reuse without widgets.
- Formatting, analysis, browser verification and artifact publication workflows.

The current Domino generator also processes Domino widget sources. Extract the
reusable transformer, but keep the choice of Domino inputs and any widget-specific
adaptations in Domino Widgets. No compatibility runtime should depend on a widget
library, its parent POM, its source archive or its build directories.

The native GWT build must continue working. Its `gwt-modular-services` artifact
currently shares source provenance with the TeaVM services; extract that source
provenance explicitly rather than losing the reference implementation or making a
GWT application depend on TeaVM runtime classes.

## Boundaries to verify

Bootstrap's GWT emulation and Domino's modular GWT services expose different Java
packages and should remain separate artifacts. Bootstrap also carries JsInterop
annotation declarations; its extracted runtime must not introduce duplicate classes
when combined with the official annotations used by Elemental2. Resolve that
boundary with dependency and browser checks before changing consumers.

The binding generator is a build dependency. Runtime compatibility JARs should not
pull JavaParser or the generator into an application's runtime dependency graph.

## Extraction sequence

1. Give the binding generator explicit inputs and isolate the shared source inputs.
2. Build and test the compatibility artifacts in the independent repository,
   including the existing GWT API and native browser contracts.
3. Publish the new artifacts and BOM to the user's `cstainton/teavm-compat` origin.
4. Update Domino and Bootstrap to consume them, verifying each from an independent
   checkout with an empty Maven cache.
5. Preserve the former coordinates with Maven relocation POMs during the transition,
   rather than publishing two implementations of the same Java packages.
6. Remove the old widget-owned modules once their independent consumers pass both
   compiler builds and browser checks.

This document records the proposed boundary; the modules and published coordinates
have not been moved or renamed yet.
