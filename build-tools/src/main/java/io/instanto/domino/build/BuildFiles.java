package io.instanto.domino.build;

import com.google.gson.*;
import java.io.IOException;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

final class BuildFiles {
  private static final Gson JSON =
      new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();

  static JsonObject object(Path path) throws IOException {
    return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
  }

  static void json(Path path, Object value) throws IOException {
    write(path, JSON.toJson(value) + "\n");
  }

  static void write(Path path, String value) throws IOException {
    Files.createDirectories(path.getParent());
    Files.writeString(path, value);
  }

  static String hash(byte[] bytes) {
    try {
      return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  static void verifyHash(Path path, String expected) throws IOException {
    require(hash(Files.readAllBytes(path)).equals(expected), "Checksum mismatch: " + path);
  }

  static List<Path> files(Path root) throws IOException {
    if (!Files.exists(root)) return List.of();
    try (var paths = Files.walk(root)) {
      return paths.filter(Files::isRegularFile).sorted().toList();
    }
  }

  static void deleteTree(Path root) throws IOException {
    if (Files.exists(root))
      try (var paths = Files.walk(root)) {
        for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.delete(path);
      }
  }

  static Path within(Path root, String name) throws IOException {
    Path base = root.toAbsolutePath().normalize();
    Path path = base.resolve(name).normalize();
    require(path.startsWith(base), "Path escapes build directory: " + name);
    return path;
  }

  static void require(boolean condition, String message) throws IOException {
    if (!condition) throw new IOException(message);
  }

  static Element xml(Path path) throws Exception {
    var factory = DocumentBuilderFactory.newInstance();
    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
    factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    return factory.newDocumentBuilder().parse(path.toFile()).getDocumentElement();
  }

  static List<Element> elements(Element root, String tag) {
    List<Element> result = new ArrayList<>();
    var nodes = root.getElementsByTagName(tag);
    for (int i = 0; i < nodes.getLength(); i++) result.add((Element) nodes.item(i));
    return result;
  }

  static Map<String, String> attributes(Element element, String... names) {
    Map<String, String> values = new LinkedHashMap<>();
    for (String name : names)
      values.put(name, element.hasAttribute(name) ? element.getAttribute(name) : null);
    return values;
  }

  static List<Path> modules(Path root) throws IOException {
    try (var paths = Files.list(root)) {
      return paths.filter(p -> Files.isRegularFile(p.resolve("pom.xml"))).sorted().toList();
    }
  }

  static String html(String text) {
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
  }

  private BuildFiles() {}
}
