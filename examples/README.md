# Independent applications

These minimal GWT and TeaVM applications import the published BOM and depend on one backend plus the assets JAR. They do not inherit the library reactor parent or read generated sources, vendored archives or sibling checkouts.

Use JDK 21 and Maven 3.9+. Add GitHub Packages credentials for server ID `github` (a token with package read access), then run `mvn clean verify` and `python3 prepare.py`. Serve this directory on localhost and open `gwt/target/site/` or `teavm/target/site/`.

The publish workflow copies this entire directory outside the checkout, builds with an empty Maven repository after deploying artifacts, and runs real browser input/calendar/asset checks in Chromium, Firefox and WebKit. This distinguishes published-artifact consumption from a reactor or locally installed build.
