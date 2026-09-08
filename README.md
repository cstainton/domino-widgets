# Domino Widgets

Domino Widgets adds TeaVM support to [DominoKit’s Domino UI](https://github.com/DominoKit/domino-ui).
Use its Java API to build forms, calendars, tables and dialogs in the browser.
A GWT build from the same pinned sources is also provided.

The widgets are the work of **DominoKit and its contributors**. This distribution
adds the compatibility layers and build support for both compilers while preserving
the original `org.dominokit.domino.ui` packages. Attribution and source provenance
are recorded in [NOTICE](NOTICE).

We changed the Maven groupId to `io.instanto` to distinguish this port from upstream
DominoKit releases and avoid confusion about its origin or ownership. This is an
independently maintained distribution, not an official DominoKit release.

**[Try the GWT showcase](https://cstainton.github.io/domino-widgets/gwt/)** ·
**[Try the TeaVM showcase](https://cstainton.github.io/domino-widgets/teavm/)**

## Choose your compiler

Choose the artifact that matches your application's compiler:

| Compiler | Widget artifact |
|---|---|
| GWT 2.13.1 | `domino-widgets-gwt` |
| TeaVM 0.15.0 | `domino-widgets-teavm` |

Both provide the same Java packages, so use one widget artifact per application.
Add `domino-widgets-assets` for the matching styles, fonts and icons.

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
```

Configure Maven credentials for the server ID `github`, using a token with package
read access. GitHub Packages requires authentication for public Maven downloads too.
Keep credentials in your Maven settings, outside the project POM.

Import the BOM to keep the widget and asset versions together. This example selects
TeaVM; for GWT, change `domino-widgets-teavm` to `domino-widgets-gwt`.

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

The [small example applications](examples/README.md) include complete compiler
configuration for each choice. They build with JDK 21 and Maven 3.9+, targeting
Java 17.

For GWT, inherit the widget module in your application's `.gwt.xml`:

```xml
<inherits name="org.dominokit.domino.ui.DominoUI"/>
```

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
The examples [unpack the assets during Maven packaging](examples/pom.xml) and
[copy them into the site](examples/prepare.py).

## Create a widget

Create widgets in Java and attach their elements to the page. This code works with
either compiler:

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

Call `Screen.mount()` from your GWT entry point's `onModuleLoad()`, or from your
TeaVM application's `main(String[] args)`, after the page body exists. The
[example screen](examples/teavm/src/main/java/example/client/Screen.java) adds a
button and click listener using the same approach.

## Explore the widgets

The showcases are adapted from [DominoKit’s original demo](https://github.com/DominoKit/domino-ui-demo).
They retain 51 original pages and 167 sample methods, presented through a shared
launcher for GWT and TeaVM. Use the grouped navigation or widget search to browse
the gallery. Each page links to its Java example and upstream counterpart. Browse
the [shared examples](showcase-shared/src/main/java/io/instanto/domino/client).

Both showcases use the same widget and example sources. Check the
[coverage guide](docs/COMPATIBILITY.md) when choosing a feature: it describes the
interactions tested on each compiler and the remaining limitations.

## Go further

- [Run the example applications](examples/README.md).
- [Browse the included showcase pages](docs/SHOWCASE.md).
- [Understand the port design](docs/DESIGN.md).
- [Build and test the library](docs/DEVELOPMENT.md).
- [Read the verification reports](reports/README.md).

The Java libraries are distributed under Apache-2.0 with the original notices
preserved. See [NOTICE](NOTICE) and the [asset inventory](docs/ASSETS.md) for source,
font and icon attribution.
