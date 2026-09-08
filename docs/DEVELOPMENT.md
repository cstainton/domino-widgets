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

Open `/showcase-gwt/target/site/` or `/showcase-teavm/target/site/`. The screen and gallery live once in `showcase-shared/`; launchers only invoke them. The All examples selector opens the complete included gallery on either backend. See [showcase provenance and included pages](../docs/SHOWCASE.md). CSS and fonts come from the same pinned archive as the Java sources.

See the [port design](DESIGN.md) for compatibility layers and source generation,
and the [upstream update assessment](UPSTREAM.md) for outstanding source changes.
