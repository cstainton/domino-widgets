# Browser tests

Write a scenario once in [Gherkin](../browser-tests/common/src/test/resources/features).
Cucumber Tea generates JUnit tests from the feature files and Java step definitions.
This follows the Bootstrap Widgets approach, including Mockatcha's framed-application
support for exercising the actual compiled showcase.

## Run a focused check

Build the sites as described in [development](DEVELOPMENT.md), then run:

```sh
mvn -f browser-tests/teavm/pom.xml -Dtest=WidgetStepsTest test
mvn -f browser-tests/webkit/pom.xml -Dtest=WidgetStepsTest test
```

Chrome and Firefox execute the generated Java tests through TeaVMTestRunner.
Mockatcha DOM owns each application's frame and supplies queries, input events,
assertions and asynchronous waits. Java Playwright runs the same features in WebKit.
Each scenario starts with a new application and checks startup errors, failed
resource loads and unhandled rejections before closing it.

The JVM fixture host uses `mockatcha-browser-testkit` to stage and instrument the
compiled application. It serves a real loopback upload endpoint and records the
multipart request. The tests do not mock widget implementations, fetch responses or
the server's upload result. The upload frame keeps the runner's origin and uses a
base URL to reach the CORS-enabled fixture server.

## Preserved coverage

| Feature | Scenarios per engine | Previous suite |
|---|---:|---|
| `contracts.feature` | 11 | `contracts.spec.js` |
| `interactions.feature` | 10 | `interactions.spec.js` |
| `gallery.feature` | 51 | `gallery.spec.js` |
| `native.feature` | 1 | `native.spec.js` |
| `reuse.feature` | 1 | `reuse.spec.js` |
| `external.feature` | 1, during publication | `external.spec.js` |

The normal suite has 74 scenarios per engine, or 222 across Chrome, Firefox and
WebKit. The gallery outline names every route in the pinned showcase manifest.
Recording verification results rejects missing routes, missing suites, failures,
skips and differences in the scenario sets executed by the three engines.

The feature tag `@skip-jvm` keeps TeaVMTestRunner from also attempting these browser
scenarios as plain JVM tests. The WebKit module deliberately uses its own JUnit
runner for the same generated methods: those tests execute on the JVM while driving
a real WebKit browser. They are not skipped there.

Mockatcha dispatches DOM events; those checks alone do not certify trusted native
input or browser default keyboard actions. WebKit uses real Playwright input, and
the separate Java `platform-checks` module retains native wheel checks in all three
engines, touch scrolling in Chromium, and showcase presentation checks. Unsupported
native touch injection in Firefox and WebKit remains explicitly skipped.

## Published consumers and reports

The publication workflow builds `examples/` outside the checkout with an empty Maven
cache. It runs `ExternalStepsTest` in each browser against that independently built
application, using `-Ddomino.external.root=/path/to/copied/examples`.

JUnit XML is written to each module's `target/surefire-reports-<engine>` directory.
Set `-Dbrowser.engine=firefox` alongside `-Dteavm.junit.js.runner=browser-firefox`
to retain the Chrome and Firefox reports separately. The Maven `record-results` goal assembles
the verified reports and source metadata; CI uploads the raw XML as well.

The Node Playwright test suite, npm package files and Python test server have been
replaced. Java Playwright supplies its internal runtime; a separate Node/npm setup
is not part of the test commands. Source intake, packaging and report assembly are
also handled by Maven; see the [build stages](DEVELOPMENT.md#maven-build-stages).
