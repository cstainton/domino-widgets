# Developing Domino Widgets

Run these commands from the repository root. For application setup, start with the
[usage guide](../README.md).

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

Open `/showcase-gwt/target/site/` or `/showcase-teavm/target/site/`. The screen and gallery live once in `showcase-shared/`; launchers only invoke them. The grouped navigation and widget search open the included gallery on either backend. The default route is the public gallery home; lifecycle fixtures use `?page=contracts` and native API fixtures use `?page=browser-apis`. See [showcase provenance and included pages](../docs/SHOWCASE.md). CSS and fonts come from the same pinned archive as the Java sources.

See the [port design](DESIGN.md) for compatibility layers and source generation,
and the [upstream update assessment](UPSTREAM.md) for outstanding source changes.

## Format Java sources

The build checks Java formatting during `validate`, using Spotless with a pinned
Google Java Format version. Format maintained sources and the extracted examples with:

```sh
mvn -N spotless:apply
mvn -N spotless:check
```

The original files under `upstream/` and generated files under `target/` are outside
the formatting scope. When regenerating showcase examples, prepare the same formatter
and then run the generator:

```sh
mvn -N initialize
python3 scripts/generate-showcase.py
python3 scripts/generate-showcase.py --check
```

The generator formats the extracted Java before writing or comparing it. Original
source hashes and adaptation rules still provide the provenance check.

## Check native scrolling

After compiling and preparing both sites, run the Java browser checks:

```sh
mvn -f browser-tests/platform-checks/pom.xml test
```

These checks start their own loopback server. They exercise native wheel scrolling
in Chromium, Firefox and WebKit, and native touch gestures in Chromium. The touch
checks scroll from the top to the footer and back on the home, buttons and forms
pages. They use browser input rather than scripted `scrollTo`, which can move content
even when CSS has disabled user scrolling. Native touch tests are explicitly skipped
on Firefox and WebKit because the CDP gesture API is Chromium-specific.
