# Domino Widgets

Standalone, shared-source Domino UI libraries for **GWT 2.13.1** and **TeaVM 0.15.0**. Both backends compile the same pinned original `org.dominokit.domino.ui` widget sources. There is no Verrai, Sarto or CDI dependency.

The gallery now contains 51 original pages and 167 original sample methods. Shared contracts cover core widgets, calendar navigation, table selection/pagination, trees, rich text, uploads, dynamic suggestions and browser APIs. See [coverage and limitations](docs/COMPATIBILITY.md) for the exact tested behaviors and remaining work.

[Hosted showcases](https://cstainton.github.io/domino-widgets/) offer both compiler builds. Pages deploys only after the development and production CI contracts pass.

## Build and test

Prerequisites: JDK 21, Maven 3.9+, Python 3 and Node.js.

```sh
mvn clean verify
python3 scripts/check-analysis.py
python3 scripts/check-artifacts.py
python3 scripts/member-inventory.py
python3 scripts/prepare-sites.py
npm --prefix browser-tests ci
BROWSERS=chromium,firefox,webkit npm --prefix browser-tests test
python3 scripts/spotbugs-index.py
```

Install the matching Playwright browsers before running the suite:

```sh
cd browser-tests
npx playwright install --with-deps chromium firefox webkit
BROWSERS=chromium,firefox,webkit npm test
```

`mvn -Pproduction clean verify` enables GWT obfuscation and TeaVM advanced optimization/minification. Run the same browser commands afterward. CI runs both build modes and all three browser engines. SpotBugs runs on normal builds; `-Dspotbugs.skip=true` is an explicit fast-development option. Analyzer errors fail the build; findings and missing-class diagnostics are reported separately in `target/spotbugs/index.html` (the full reactor generates the index automatically).

Serve the two launchers after preparing the sites:

```sh
python3 -m http.server 8080 --bind 127.0.0.1
```

Open `/showcase-gwt/target/site/` or `/showcase-teavm/target/site/`. The screen and gallery live once in `showcase-shared/`; launchers only invoke them. The All examples selector opens the complete included gallery on either backend. See [showcase provenance and included pages](docs/SHOWCASE.md). CSS and fonts come from the same pinned archive as the Java sources.

## Consumption

Import `io.instanto:domino-widgets-bom:0.1.0-SNAPSHOT`, then select **exactly one** of `domino-widgets-gwt` or `domino-widgets-teavm`, and add `domino-widgets-assets`. These are project-owned coordinates; original Java packages are preserved.

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.instanto</groupId>
      <artifactId>domino-widgets-bom</artifactId>
      <version>0.1.0-SNAPSHOT</version>
      <type>pom</type><scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
<dependencies>
  <dependency>
    <groupId>io.instanto</groupId>
    <artifactId>domino-widgets-teavm</artifactId>
  </dependency>
  <dependency>
    <groupId>io.instanto</groupId>
    <artifactId>domino-widgets-assets</artifactId>
  </dependency>
</dependencies>
```

The GWT backend supplies `org.dominokit.domino.ui.DominoUI`; inherit that module in your application. Compile with source level 17. The TeaVM launcher POM demonstrates compiler/runtime setup, including the SLF4J substitution runtime. Never put original Elemental2 or `com.google.jsinterop:base` alongside the TeaVM compatibility artifacts. Both widget backends contain identical package names and must never be combined.

The assets JAR exposes `META-INF/resources/domino-widgets/`; serve that directory and load `domino-widgets/css/domino-ui/domino-ui.css`. Browser assets are packaged once, outside the Java libraries.

Maven publication uses the manual GitHub Actions workflow. After deployment it builds the [external sample applications](examples/) outside the checkout with an empty Maven repository, then tests them in all three browser engines. A release profile attaches Javadocs; source JARs are attached by default. Builds require no sibling checkouts or locally modified dependency binaries.

## Compatibility architecture

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

The existing Bootstrap compatibility library implements `com.google.gwt` APIs. This fork's service layer uses `org.gwtproject` packages, so copying Bootstrap's implementation would not satisfy these contracts. The existing Domino history/REST ports remain optional future integration work. See [prior-port findings](docs/EXISTING-PORT.md) and [migration status](docs/MIGRATION.md).

## Reproducibility

[Source lock](upstream/source-lock.json) records the corrected fork commit and archive SHA-256. [Binding locks](upstream/bindings-lock.json) pin the published Elemental2 inputs. Every build checks these hashes before generating sources; there are no moving-branch downloads. Generated files live under `target/` and must not be edited.

The complete source archive retains the original sources, checked-in generated icons, processor sources, descriptors, resources and notices. `target/intake/inventory.json` accounts for every archived file. [Seam manifest](docs/SEAMS.json) describes the bounded adaptations; [member inventory](reports/member-inventory.json) records binary field/method references rather than imports alone.

Apache-2.0; upstream copyright headers and notices are preserved. Font/icon assets retain their upstream provenance; see [asset inventory](docs/ASSETS.md).
