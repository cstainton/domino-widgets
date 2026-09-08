package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import com.google.googlejavaformat.java.Formatter;
import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import org.apache.maven.plugins.annotations.*;

/** Generate or check formatted showcase adapters from the pinned sample methods. */
@Mojo(name = "showcase", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public final class ShowcaseMojo extends BuildMojo {
  /** Write adapters explicitly; normal builds only check for drift. */
  @Parameter(property = "domino.showcase.write", defaultValue = "false")
  private boolean write;

  @Override
  protected void run() throws Exception {
    Map<Path, String> generated = generate(root());
    for (var entry : generated.entrySet()) {
      if (write) write(entry.getKey(), entry.getValue());
      else
        require(
            Files.exists(entry.getKey())
                && Files.readString(entry.getKey()).equals(entry.getValue()),
            "Regenerate showcase adapter with -Ddomino.showcase.write=true: " + entry.getKey());
    }
    getLog()
        .info(
            (write ? "Generated " : "Verified ")
                + generated.size()
                + " formatted showcase sources");
  }

  static Map<Path, String> generate(Path root) throws Exception {
    JsonObject lock = object(root.resolve("upstream/showcase-lock.json"));
    Map<Path, String> generated = new LinkedHashMap<>();
    var rows = lock.getAsJsonArray("sources");
    for (JsonElement row : rows) {
      JsonObject item = row.getAsJsonObject();
      String source = sample(root, item);
      String imports =
          String.join(
              "\n",
              source
                  .lines()
                  .filter(
                      s ->
                          s.startsWith("import ")
                              && List.of(
                                      "org.dominokit.domino.ui.",
                                      "elemental2.",
                                      "java.",
                                      "jsinterop.",
                                      "org.gwtproject.",
                                      "org.slf4j.")
                                  .stream()
                                  .anyMatch(s::contains))
                  .toList());
      List<String> methods = new ArrayList<>();
      for (String key : List.of("methods", "helpers")) {
        if (item.has(key))
          for (JsonElement name : item.getAsJsonArray(key))
            methods.add(method(source, name.getAsString()));
      }
      Path destination = within(root, text(item, "adapter"));
      String name = className(destination);
      String calls = "";
      for (JsonElement method : item.getAsJsonArray("methods"))
        calls += method.getAsString() + "();\n";
      String code =
          "// Adapted from DominoKit/domino-ui-demo at "
              + text(lock, "commit")
              + "; see upstream/showcase-lock.json.\n"
              + "package io.instanto.domino.client;\n"
              + imports
              + "\nimport elemental2.dom.HTMLDivElement;\nimport org.dominokit.domino.ui.elements.DivElement;\n"
              + "import static org.dominokit.domino.ui.utils.Domino.*;\npublic final class "
              + name
              + " implements org.dominokit.domino.ui.style.DominoCss {\n private final DivElement element=div();\n"
              + text(item, "fields")
              + "\n public HTMLDivElement render(){\n"
              + text(item, "before")
              + calls
              + text(item, "after")
              + "return element.element();}\n"
              + String.join("\n", methods)
              + "\n}\n";
      code =
          replace(code, item)
              .replace(
                  "GWT.getModuleBaseURL() + \"/images/image-gallery/9.jpg\"",
                  "\"showcase-image.jpg\"");
      if (item.has("nativeElement") && item.get("nativeElement").getAsBoolean())
        code =
            code.replace(
                    "private final DivElement element=div();",
                    "private final HTMLDivElement element=div().element();")
                .replace("return element.element();", "return element;");
      generated.put(destination, format(code));
    }
    List<String> routes = new ArrayList<>();
    StringBuilder cases = new StringBuilder();
    for (JsonElement row : rows) {
      var item = row.getAsJsonObject();
      String route = item.get("route").toString();
      routes.add(route);
      cases
          .append("if(route.equals(")
          .append(route)
          .append("))return new ")
          .append(className(Path.of(text(item, "adapter"))))
          .append("().render();\n");
    }
    generated.put(
        root.resolve("showcase-shared/src/main/java/io/instanto/domino/client/GalleryCatalog.java"),
        format(
            "package io.instanto.domino.client;\nimport elemental2.dom.HTMLElement;\npublic final class GalleryCatalog {\n"
                + "public static final java.util.List<String> ROUTES=java.util.List.of("
                + String.join(",", routes)
                + ");\npublic static HTMLElement render(String route){\n"
                + cases
                + "return null;\n}}\n"));
    if (lock.has("supporting"))
      for (JsonElement row : lock.getAsJsonArray("supporting")) {
        JsonObject item = row.getAsJsonObject();
        String code =
            sample(root, item)
                .replaceFirst("package [^;]+;", "package io.instanto.domino.client;")
                .replaceAll(
                    "(?m)^import (?:com.fasterxml.jackson|org.dominokit.domino.datatable)[^;]+;\\s*",
                    "")
                .replaceAll("@JsonIgnoreProperties\\([^)]*\\)|@JsonIgnore\\b", "");
        generated.put(
            within(
                root.resolve("showcase-shared/src/main/java/io/instanto/domino/client"),
                text(item, "name") + ".java"),
            format(
                "// Original showcase helper; see upstream/showcase-lock.json.\n"
                    + replace(code, item)));
      }
    return generated;
  }

  static String method(String source, String name) throws Exception {
    Matcher match =
        Pattern.compile(
                "    (?:private|protected|public) [^\\n]+\\b"
                    + Pattern.quote(name)
                    + "\\([^;]*?\\)\\s*\\{")
            .matcher(source);
    require(match.find(), "Missing sample method: " + name);
    int start = match.start(), opening = match.end() - 1, depth = 0;
    Matcher tokens =
        Pattern.compile(
                "\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*'|//[^\\n]*|/\\*[\\s\\S]*?\\*/|[{}]")
            .matcher(source);
    tokens.region(opening, source.length());
    while (tokens.find()) {
      if (tokens.group().equals("{")) depth++;
      else if (tokens.group().equals("}") && --depth == 0)
        return source.substring(start, tokens.end());
    }
    throw new java.io.IOException("Unbalanced sample method: " + name);
  }

  private static String format(String source) throws Exception {
    return new Formatter().formatSourceAndFixImports(source.replaceAll("(?m)^[ \\t]+$", ""));
  }

  private static String sample(Path root, JsonObject item) throws Exception {
    Path path = within(root.resolve("upstream/showcase"), text(item, "path"));
    verifyHash(path, text(item, "sha256"));
    return Files.readString(path);
  }

  private static String replace(String code, JsonObject item) throws Exception {
    if (item.has("replacements"))
      for (var replacement : item.getAsJsonObject("replacements").entrySet()) {
        require(
            code.contains(replacement.getKey()),
            "Adaptation no longer applies: " + replacement.getKey());
        code = code.replace(replacement.getKey(), replacement.getValue().getAsString());
      }
    return code;
  }

  private static String className(Path path) {
    return path.getFileName().toString().replaceFirst("\\.java$", "");
  }

  private static String text(JsonObject object, String key) {
    return object.has(key) ? object.get(key).getAsString() : "";
  }
}
