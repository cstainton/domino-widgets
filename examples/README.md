# A TeaVM application consuming published Domino Widgets

This example builds independently of the widget checkout. Configure Maven credentials
for `github` and `github-teavm-compat`, then run `mvn clean verify` here with JDK 21.
Run `python3 prepare.py` and serve this directory to open `teavm/target/site/`.

The POM imports the published widget BOM and obtains all compatibility dependencies
from the independent `cstainton/teavm-compat` package repository. No sibling sources
or reactor modules are required. For GWT applications, use DominoKit upstream.
