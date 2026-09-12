package io.instanto.domino.testing;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.instanto.webapp.testkit.app.ApplicationFiles;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/** Serves actual compiled applications and records real multipart uploads. JVM only. */
public final class FixtureServer {
  private static FixtureServer instance;
  private final Path resources;
  private final HttpServer server;
  private volatile String uploaded = "";

  public static synchronized FixtureServer start() {
    if (instance == null) {
      try {
        instance = new FixtureServer();
      } catch (IOException e) {
        throw new IllegalStateException("Could not prepare browser fixtures", e);
      }
    }
    return instance;
  }

  private FixtureServer() throws IOException {
    Path root = Path.of(System.getProperty("domino.build.root")).toAbsolutePath().normalize();
    resources = Path.of(System.getProperty("domino.test.resources")).resolve("domino-fixtures");
    Files.createDirectories(resources);
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext("/", this::serve);
    server.setExecutor(
        Executors.newCachedThreadPool(
            r -> {
              Thread thread = new Thread(r, "domino-fixtures");
              thread.setDaemon(true);
              return thread;
            }));
    server.start();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
    Map<String, Path> inputs = new LinkedHashMap<>();
    inputs.put("teavm", root.resolve("showcase-teavm/target/site"));
    inputs.put("reuse", root.resolve("compat-reuse-smoke/target/site"));
    Path external =
        Path.of(System.getProperty("domino.external.root", root.resolve("examples").toString()));
    for (String backend : new String[] {"teavm"}) {
      Path source = external.resolve(backend + "/target/site");
      if (Files.isRegularFile(source.resolve("index.html")))
        inputs.put("external-" + backend, source);
    }
    for (var entry : inputs.entrySet()) {
      Path source = entry.getValue();
      if (!Files.isRegularFile(source.resolve("index.html"))) {
        throw new IOException("Build and prepare the fixture first: " + source);
      }
      Path destination = resources.resolve(entry.getKey());
      if (Files.exists(destination)) {
        try (var files = Files.walk(destination)) {
          for (Path file : files.sorted(java.util.Comparator.reverseOrder()).toList())
            Files.delete(file);
        }
      }
      ApplicationFiles.stage(source, destination);
      Path index = resources.resolve(entry.getKey()).resolve("index.html");
      String html = Files.readString(index);
      String instrumented =
          html.replaceFirst(
              "(?i)<body[^>]*>", "$0" + java.util.regex.Matcher.quoteReplacement(DIAGNOSTICS));
      Files.writeString(index, instrumented);
      String upload =
          instrumented.replaceFirst(
              "(?i)<head>", "<head><base href=\"" + base() + entry.getKey() + "/\">");
      Files.writeString(index.resolveSibling("upload.html"), upload);
    }
  }

  public String base() {
    return "http://127.0.0.1:" + server.getAddress().getPort() + "/";
  }

  private void serve(HttpExchange exchange) throws IOException {
    try (exchange) {
      var headers = exchange.getResponseHeaders();
      headers.set("Access-Control-Allow-Origin", "*");
      headers.set("Access-Control-Allow-Headers", "*");
      headers.set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
      headers.set("Cross-Origin-Resource-Policy", "cross-origin");
      headers.set("Cache-Control", "no-store");
      String path = exchange.getRequestURI().getPath();
      if (exchange.getRequestMethod().equals("OPTIONS")) {
        exchange.sendResponseHeaders(204, -1);
        return;
      }
      if (path.endsWith("/service/upload") && exchange.getRequestMethod().equals("POST")) {
        byte[] bytes = exchange.getRequestBody().readNBytes(1_048_577);
        if (bytes.length > 1_048_576) {
          respond(exchange, 413, "text/plain", "Too large".getBytes(StandardCharsets.UTF_8));
          return;
        }
        uploaded =
            "POST\n"
                + exchange.getRequestHeaders().getFirst("Content-Type")
                + "\n"
                + new String(bytes, StandardCharsets.UTF_8);
        respond(
            exchange,
            200,
            "application/json",
            "{\"uploaded\":true}".getBytes(StandardCharsets.UTF_8));
        return;
      }
      if (path.endsWith("/last-upload")) {
        respond(exchange, 200, "text/plain", uploaded.getBytes(StandardCharsets.UTF_8));
        return;
      }
      Path file = resources.resolve(path.substring(1)).normalize();
      if (!file.startsWith(resources) || !Files.isRegularFile(file)) {
        respond(exchange, 404, "text/plain", "Not found".getBytes(StandardCharsets.UTF_8));
        return;
      }
      String type = Files.probeContentType(file);
      if (path.endsWith(".js")) type = "application/javascript";
      if (path.endsWith(".css")) type = "text/css";
      respond(
          exchange,
          200,
          type == null ? "application/octet-stream" : type,
          Files.readAllBytes(file));
    }
  }

  private static void respond(HttpExchange exchange, int status, String type, byte[] body)
      throws IOException {
    exchange.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
    exchange.sendResponseHeaders(status, body.length);
    exchange.getResponseBody().write(body);
  }

  // Collect diagnostics before the application starts; assertions live in the Java steps.
  private static final String DIAGNOSTICS =
      """
      <script>
      window.__dominoTest = {errors: [], console: [], uploaded: ''};
      window.addEventListener('error', function(e) {
        var target = e.target;
        window.__dominoTest.errors.push(e.message || 'Resource failed: ' + (target.src || target.href || target.tagName));
      }, true);
      window.addEventListener('unhandledrejection', function(e) { window.__dominoTest.errors.push(String(e.reason)); });
      ['log','info','warn','error'].forEach(function(level) {
        var original = console[level];
        console[level] = function() {
          window.__dominoTest.console.push(Array.from(arguments).join(' '));
          return original.apply(console, arguments);
        };
      });
      </script>
      """;
}
