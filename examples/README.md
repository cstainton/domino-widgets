# A TeaVM application consuming published Domino Widgets

This example builds independently of the widget checkout. Configure Maven credentials
for `github` and `github-teavm-compat`, then run `mvn clean verify` here with JDK 21.
The Maven build assembles `teavm/target/site/`, including the launcher and assets.
Serve it with `jwebserver -b 127.0.0.1 -p 8080 -d teavm/target/site` and open
`http://127.0.0.1:8080/`.

The POM imports the published widget BOM and obtains all compatibility dependencies
from the independent `cstainton/teavm-compat` package repository. No sibling sources
or reactor modules are required. For GWT applications, use DominoKit upstream.
