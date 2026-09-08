# Port design

Domino Widgets provides the TeaVM adaptation of DominoKit’s Domino UI. Widget API,
rendering and behavior come from DominoKit. GWT users should use upstream directly;
this repository no longer maintains or publishes a separate GWT distribution.

The pinned source archive retains provenance, including fixes submitted upstream.
Java packages and copyright headers remain unchanged. The build verifies the
archive and selects Domino sources for the separately published
`jsinterop-binding-generator`. Widget-specific source selection stays here.

Elemental2, JsInterop base and modular service compatibility implementations now
live in [teavm-compat](https://github.com/cstainton/teavm-compat), with an independent
parent, BOM, immutable inputs, tests and publication workflow. Elemental2 source JARs
and their lock file are owned there and are no longer bundled in this widget repository. The runtime widget
JAR depends on compatibility APIs, not JavaParser or the generator.

Published widget artifacts are `domino-widgets-teavm`, `domino-widgets-assets` and
`domino-widgets-bom`, under `io.instanto`. Former TeaVM compatibility artifact names
remain relocation POMs during migration. The GWT widget and reference service
artifacts are no longer published.

The showcase retains adapted upstream examples and links each page to DominoKit's
upstream showcase. Historical reports include the previous GWT baseline; current
verification covers TeaVM in Chromium, Firefox and WebKit, in both build modes.

See [the extraction record](COMPAT-EXTRACTION.md), [showcase provenance](SHOWCASE.md),
[coverage](COMPATIBILITY.md) and [development commands](DEVELOPMENT.md).
