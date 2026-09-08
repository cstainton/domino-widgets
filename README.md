# Domino Widgets

Domino Widgets adds TeaVM support to [DominoKit’s Domino UI](https://github.com/DominoKit/domino-ui).
Use its Java API to build forms, calendars, tables and dialogs in the browser.
This repository provides the TeaVM adaptation. For GWT, use DominoKit’s upstream distribution.

The widgets are the work of **DominoKit and its contributors**. This distribution
adds TeaVM build support while preserving
the original `org.dominokit.domino.ui` packages. Attribution and source provenance
are recorded in [NOTICE](NOTICE).

We changed the Maven groupId to `io.instanto` to distinguish this port from upstream
DominoKit releases and avoid confusion about its origin or ownership. This is an
independently maintained distribution, not an official DominoKit release.

**[Try the TeaVM showcase](https://cstainton.github.io/domino-widgets/teavm/)**

Use `domino-widgets-teavm` for the widgets and `domino-widgets-assets` for the matching
styles, fonts and icons. The shared [TeaVM compatibility libraries](https://github.com/cstainton/teavm-compat)
are brought in as dependencies.

## Add the dependencies

The current version is `0.1.0-SNAPSHOT`, published under `io.instanto` in GitHub
Packages. Add this repository inside your POM's `<repositories>` element:

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/cstainton/domino-widgets</url>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
<repository>
  <id>github-teavm-compat</id>
  <url>https://maven.pkg.github.com/cstainton/teavm-compat</url>
</repository>
```

Configure Maven credentials for the server IDs `github` and `github-teavm-compat`, using a token with package
read access. GitHub Packages requires authentication for public Maven downloads too.
Keep credentials in your Maven settings, outside the project POM.

Import the BOM to keep the widget and asset versions together. This example selects
the TeaVM widget artifact.

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.instanto</groupId>
      <artifactId>domino-widgets-bom</artifactId>
      <version>0.1.0-SNAPSHOT</version>
      <type>pom</type>
      <scope>import</scope>
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

The [small example application](examples/README.md) include complete compiler
configuration. It builds with JDK 21 and Maven 3.9+, targeting
Java 17.

For TeaVM, follow the [example POM](examples/teavm/pom.xml), including its SLF4J
runtime configuration. Its dependencies supply the Elemental2 and JsInterop
compatibility layers; adding the original Elemental2 or `com.google.jsinterop:base`
artifacts alongside them creates duplicate packages.

## Load the styles

The assets JAR contains `META-INF/resources/domino-widgets/`. Copy or serve that
folder as `domino-widgets/` beside your application's HTML, then add:

```html
<link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css">
```

Keep the folder structure intact so the stylesheet can find its fonts and icons.
The examples [unpack the assets directly into the site during Maven packaging](examples/pom.xml).

## Create a widget

Create widgets in Java and attach their elements to the page. For example:

```java
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.datepicker.Calendar;
import org.dominokit.domino.ui.forms.TextBox;

public final class Screen {
    public static void mount() {
        TextBox name = TextBox.create("Your name");
        Calendar calendar = Calendar.create();

        DomGlobal.document.body.appendChild(name.element());
        DomGlobal.document.body.appendChild(calendar.element());
    }
}
```

Call `Screen.mount()` from your TeaVM application's `main(String[] args)`, after the page body exists. The
[example screen](examples/teavm/src/main/java/example/client/Screen.java) adds a
button and click listener using the same approach.

## Explore the widgets

The showcases are adapted from [DominoKit’s original demo](https://github.com/DominoKit/domino-ui-demo).
They retain 51 original pages and 167 sample methods, presented through a shared
TeaVM launcher. Use the grouped navigation or widget search to browse
the gallery. Each page links to its Java example and upstream counterpart. Browse
the [shared examples](showcase-shared/src/main/java/io/instanto/domino/client).

Check the
[coverage guide](docs/COMPATIBILITY.md) when choosing a feature: it describes the
interactions tested across browsers and the remaining limitations.

## Go further

- [Run the example applications](examples/README.md).
- [Browse the included showcase pages](docs/SHOWCASE.md).
- [Understand the port design](docs/DESIGN.md).
- [Build and test the library](docs/DEVELOPMENT.md).
- [Read the verification reports](reports/README.md).

The Java libraries are distributed under Apache-2.0 with the original notices
preserved. See [NOTICE](NOTICE) and the [asset inventory](docs/ASSETS.md) for source,
font and icon attribution.

## Support the projects

Like DominoKit? Please [support the upstream project](https://www.patreon.com/Dominokit).

Want to see more TeaVM libraries maintained and supported? Please [support us](https://github.com/sponsors/cstainton).
