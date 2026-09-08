package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import org.apache.maven.plugins.annotations.*;
import org.w3c.dom.Element;

/** Record verification evidence only after every browser passed the same scenarios. */
@Mojo(name = "record-results", threadSafe = true)
public final class ResultsMojo extends BuildMojo {
  /** Build mode recorded in the report, matching the tested compilation. */
  @Parameter(property = "domino.mode", defaultValue = "development")
  private String mode;

  private static final Map<String, Integer> EXPECTED =
      Map.of(
          "GalleryStepsTest", 51, "WidgetStepsTest", 21, "NativeStepsTest", 1, "ReuseStepsTest", 1);

  @Override
  protected void run() throws Exception {
    require(List.of("development", "production").contains(mode), "Unknown build mode: " + mode);
    Path root = root(), out = root.resolve("reports/" + mode);
    List<Map<String, Object>> projects = new ArrayList<>();
    Set<String> first = null;
    Map<Path, String> reports = new LinkedHashMap<>();
    for (String engine : List.of("chrome", "firefox", "webkit")) {
      String module = engine.equals("webkit") ? "webkit" : "teavm";
      Path directory =
          root.resolve("browser-tests/" + module + "/target/surefire-reports-" + engine);
      List<Map<String, String>> suites = new ArrayList<>();
      List<Map<String, Object>> cases = new ArrayList<>();
      Set<String> names = new TreeSet<>();
      for (String name : new TreeSet<>(EXPECTED.keySet())) {
        Path report = directory.resolve("TEST-io.instanto.domino.testing." + name + ".xml");
        Element suite = xml(report);
        validateSuite(suite, EXPECTED.get(name), report.toString());
        suites.add(attributes(suite, "name", "tests", "failures", "errors", "skipped", "time"));
        for (Element testcase : elements(suite, "testcase")) {
          String cls = testcase.getAttribute("classname"), test = testcase.getAttribute("name");
          require(names.add(cls + "#" + test), "Duplicate scenario in " + report + ": " + test);
          cases.add(
              Map.of(
                  "class",
                  cls,
                  "name",
                  test,
                  "seconds",
                  Double.parseDouble(testcase.getAttribute("time"))));
        }
        reports.put(
            out.resolve("junit/" + engine + "/" + report.getFileName()),
            Files.readString(report).replace(root.toString(), "."));
      }
      if (first == null) first = names;
      else
        require(first.equals(names), "Browser engines did not execute the same Cucumber scenarios");
      projects.add(Map.of("name", module + "-" + engine, "suites", suites, "scenarios", cases));
    }
    JsonObject showcase = object(root.resolve("upstream/showcase-lock.json"));
    Set<String> routes = new TreeSet<>(), examples = new TreeSet<>();
    int methods = 0;
    for (var row : showcase.getAsJsonArray("sources")) {
      routes.add(row.getAsJsonObject().get("route").getAsString());
      methods += row.getAsJsonObject().getAsJsonArray("methods").size();
    }
    for (String line :
        Files.readAllLines(
            root.resolve("browser-tests/common/src/test/resources/features/gallery.feature"))) {
      if (line.strip().startsWith("|"))
        examples.add(line.strip().replaceAll("^\\||\\|$", "").strip());
    }
    examples.remove("route");
    require(routes.equals(examples), "Gallery feature routes differ from the pinned showcase");
    var stats =
        Map.of(
            "expected",
            Objects.requireNonNull(first).size() * projects.size(),
            "unexpected",
            0,
            "skipped",
            0,
            "flaky",
            0);
    Map<String, Object> browser =
        Map.of(
            "runner",
            "Cucumber Tea / TeaVMTestRunner / Java Playwright WebKit",
            "config",
            Map.of("projects", projects),
            "stats",
            stats);
    List<Map<String, String>> unitTests = new ArrayList<>();
    for (Path module : modules(root)) {
      for (Path report : files(module.resolve("target/surefire-reports")))
        if (report.getFileName().toString().matches("TEST-.*\\.xml")) {
          unitTests.add(attributes(xml(report), "name", "tests", "failures", "errors", "skipped"));
          reports.put(
              out.resolve(report.getFileName()),
              Files.readString(report).replace(root.toString(), "."));
        }
    }
    Map<String, Object> summary = new LinkedHashMap<>();
    summary.put("mode", mode);
    summary.put("source", object(root.resolve("upstream/source-lock.json")));
    summary.put("browser", stats);
    summary.put("compilers", Map.of("teavm", "0.15.0", "jdk", "21", "javaRelease", 17));
    summary.put("unitTests", unitTests);
    summary.put(
        "bundleBytes",
        Map.of("teavm", Files.size(root.resolve("showcase-teavm/target/site/showcase.js"))));
    summary.put("showcase", Map.of("pages", routes.size(), "sampleMethods", methods));
    summary.put("browserProjects", projects.stream().map(p -> p.get("name")).toList());
    summary.put(
        "spotbugs",
        JsonParser.parseString(Files.readString(root.resolve("target/spotbugs/index.json"))));
    Map<String, String> manifest = new TreeMap<>();
    Path generated = root.resolve("target/compat");
    for (Path path : files(generated))
      if (path.toString().endsWith(".java"))
        manifest.put(generated.relativize(path).toString(), hash(Files.readAllBytes(path)));
    require(!manifest.isEmpty(), "Missing generated Java sources");
    // Publish reports only after all validation has succeeded.
    for (var report : reports.entrySet()) write(report.getKey(), report.getValue());
    json(out.resolve("browser.json"), browser);
    json(out.resolve("summary.json"), summary);
    json(out.resolve("generated-sha256.json"), manifest);
    getLog().info("Recorded " + mode + ": " + stats);
  }

  static void validateSuite(Element suite, int count, String report) throws Exception {
    require(Integer.parseInt(suite.getAttribute("tests")) == count, "Incomplete suite: " + report);
    for (String key : List.of("failures", "errors", "skipped"))
      require(
          Integer.parseInt(suite.getAttribute(key)) == 0, "Failing or skipped suite: " + report);
    require(
        elements(suite, "testcase").size() == count,
        "Testcase count does not match suite total: " + report);
    require(
        elements(suite, "failure").isEmpty()
            && elements(suite, "error").isEmpty()
            && elements(suite, "skipped").isEmpty(),
        "Failing testcase: " + report);
  }
}
