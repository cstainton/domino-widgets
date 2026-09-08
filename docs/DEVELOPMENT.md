# Developing Domino Widgets

Run these commands from the repository root. For application setup, start with the
[usage guide](../README.md).

## Build and test


Prerequisites: JDK 21, Maven 3.9+, Chrome and Firefox. Maven authenticates
GitHub Packages repositories for `teavm-compat`, `cucumber-tea`, `gherkin-tea` and
`mockatcha` with a package-read token. No separately installed Node.js or npm is
required for these tests; Java Playwright manages its own internal driver runtime.

```sh
mvn clean install
mvn -f browser-tests/webkit/pom.xml org.codehaus.mojo:exec-maven-plugin:3.6.3:java \
  -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test \
  -Dexec.args="install --with-deps chromium firefox webkit"
mvn -f browser-tests/teavm/pom.xml test
mvn -f browser-tests/teavm/pom.xml -Dbrowser.engine=firefox \
  -Dteavm.junit.js.runner=browser-firefox test
mvn -f browser-tests/webkit/pom.xml test
mvn -f browser-tests/platform-checks/pom.xml test
mvn -N io.instanto:domino-build-maven-plugin:0.1.0-SNAPSHOT:record-results -Ddomino.mode=development
```

The [shared Gherkin features](../browser-tests/common/src/test/resources/features)
run through Cucumber Tea's generated JUnit tests. Chrome and Firefox use
TeaVMTestRunner and Mockatcha DOM against compiled applications in same-origin
frames. WebKit runs the same feature assertions through Java Playwright. The
[testing guide](TESTING.md) describes the drivers, coverage and native input limits.

`mvn -Pproduction clean verify` enables advanced optimization and minification.
Repeat the browser commands, recording results with `-Ddomino.mode=production`.
CI checks both modes in all three engines. SpotBugs runs on normal builds;
`-Dspotbugs.skip=true` is an explicit fast-development option. Analyzer errors fail
the build; findings and missing-class diagnostics are reported separately in
`target/spotbugs/index.html`.

Both `mvn clean verify` and `mvn clean install` assemble the sites and run source,
packaging and analysis checks. `install` also makes the repository’s build plugin
available for the standalone reporting and maintenance goals below.

Serve the prepared showcase using the JDK’s HTTP server:

```sh
jwebserver -b 127.0.0.1 -p 8080 -d showcase-teavm/target/site
```

Open `http://127.0.0.1:8080/`. The screen and gallery live once in `showcase-shared/`; launchers only invoke them. The grouped navigation and widget search open the included gallery on TeaVM. The default route is the public gallery home; lifecycle fixtures use `?page=contracts` and native API fixtures use `?page=browser-apis`. See [showcase provenance and included pages](../docs/SHOWCASE.md). CSS and fonts come from the same pinned archive as the Java sources.

See the [port design](DESIGN.md) for compatibility layers and source generation,
and the [upstream update assessment](UPSTREAM.md) for outstanding source changes.

## Format Java sources

The build automatically applies Java formatting during `validate`, using Spotless with a pinned
Google Java Format version. Format maintained sources and the extracted examples with:

```sh
mvn -N spotless:apply
mvn -N spotless:check
```

The original files under `upstream/` and generated files under `target/` are outside
the formatting scope. After `mvn install`, regenerate or check the pinned showcase
adapters with the Java Maven plugin:

```sh
mvn -N io.instanto:domino-build-maven-plugin:0.1.0-SNAPSHOT:showcase -Ddomino.showcase.write=true
mvn -N io.instanto:domino-build-maven-plugin:0.1.0-SNAPSHOT:showcase
```

The generator formats the extracted Java before writing or comparing it. Original
source hashes and adaptation rules still provide the provenance check.

## Check native scrolling

After building the TeaVM site, run the Java browser checks:

```sh
mvn -f browser-tests/platform-checks/pom.xml test
```

These checks start their own loopback server. They exercise native wheel scrolling
in Chromium, Firefox and WebKit, and native touch gestures in Chromium. The touch
checks scroll from the top to the footer and back on the home, buttons and forms
pages. They use browser input rather than scripted `scrollTo`, which can move content
even when CSS has disabled user scrolling. Native touch tests are explicitly skipped
on Firefox and WebKit because the CDP gesture API is Chromium-specific.

## Maven build stages

The reactor builds [the Java Maven plugin](../build-tools) before using it. These
are compiled Maven goals with typed parameters and build failures, with no script
interpreter or subprocess wrapper. Standard Maven plugins handle site files:

| Stage | Work |
|---|---|
| `build-inputs:initialize` | Verify both upstream locks and extract the pinned archive; concatenate the matching component CSS. |
| `build-inputs:generate-sources` | Check the maintained showcase adapters against the pinned samples using Google Java Format. |
| `prepare-package` / `package` | Resources copies launcher HTML, gallery CSS and images; Dependency unpacks the asset JAR into each site. The standalone example does the same in its own build. |
| `build-verification:verify` | Gate SpotBugs reports, reject duplicate classes or bundled assets, verify generated-source hashes and write the external member inventory. |
| `record-results` | After browser tests, validate the same complete scenario set in all engines and assemble verification metadata. |

`.mvn/jvm.config` grants the pinned formatter access to the JDK compiler APIs it
uses in process. JDK 21 and Maven are the build prerequisites; Python is not needed.

After testing production, assemble Pages without recompiling or changing the tested
JavaScript:

```sh
mvn -Ppages -pl build-verification resources:copy-resources@pages
```

The output is `target/pages/`. The regular `clean` lifecycle removes previous build
outputs. `mvn -Pproduction,pages clean verify` can also assemble Pages as part of a
fresh build; CI uploads the site only after its browser checks pass.
