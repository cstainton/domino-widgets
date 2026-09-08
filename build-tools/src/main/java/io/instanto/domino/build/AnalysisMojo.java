package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import org.apache.maven.plugins.annotations.*;
import org.w3c.dom.Element;

/** Index SpotBugs results and enforce the explicit high-priority triage manifest. */
@Mojo(name = "analysis", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public final class AnalysisMojo extends BuildMojo {
  /** Skip analysis only when the build explicitly disables SpotBugs. */
  @Parameter(property = "spotbugs.skip", defaultValue = "false")
  private boolean skip;

  /** Produce diagnostics after a failed build without enforcing the gate again. */
  @Parameter(property = "domino.analysis.indexOnly", defaultValue = "false")
  private boolean indexOnly;

  @Override
  protected void run() throws Exception {
    if (skip) {
      getLog().info("Skipping analysis gate because spotbugs.skip=true");
      return;
    }
    List<Map<String, Object>> rows = new ArrayList<>();
    List<String> problems = new ArrayList<>();
    Set<List<String>> allowed = new HashSet<>();
    for (var item :
        JsonParser.parseString(Files.readString(root().resolve("docs/spotbugs-triage.json")))
            .getAsJsonArray()) {
      var obj = item.getAsJsonObject();
      List<String> key = new ArrayList<>();
      for (String name : List.of("type", "class", "field", "method"))
        key.add(obj.get(name).isJsonNull() ? null : obj.get(name).getAsString());
      allowed.add(key);
    }
    for (Path module : modules(root())) {
      String name = module.getFileName().toString();
      Path report = module.resolve("target/spotbugs/" + name + "-spotbugs.xml");
      // The build plugin's artifactId differs from its directory name.
      if (name.equals("build-tools"))
        report = module.resolve("target/spotbugs/domino-build-maven-plugin-spotbugs.xml");
      if (!Files.exists(report)) {
        if (files(module.resolve("target/classes")).stream()
            .anyMatch(p -> p.toString().endsWith(".class"))) {
          problems.add(name + ": missing report");
          rows.add(Map.of("module", name, "status", "missing-report"));
        }
        continue;
      }
      Element xml = xml(report);
      List<Element> bugs = elements(xml, "BugInstance");
      Map<String, String> errors = new LinkedHashMap<>();
      if (!elements(xml, "Errors").isEmpty()) {
        Element error = elements(xml, "Errors").get(0);
        for (int i = 0; i < error.getAttributes().getLength(); i++) {
          var attr = error.getAttributes().item(i);
          errors.put(attr.getNodeName(), attr.getNodeValue());
        }
        if (Integer.parseInt(errors.getOrDefault("errors", "0")) != 0
            || Integer.parseInt(errors.getOrDefault("missingClasses", "0")) != 0)
          problems.add(name + ": analyzer errors/missing classes " + errors);
      }
      int high = 0;
      for (Element bug : bugs)
        if (bug.getAttribute("priority").equals("1")) {
          high++;
          var key =
              Arrays.asList(
                  bug.getAttribute("type"),
                  childAttribute(bug, "Class", "classname"),
                  childAttribute(bug, "Field", "name"),
                  childAttribute(bug, "Method", "name"));
          if (!allowed.contains(key)) problems.add(name + ": untriaged " + key);
        }
      Map<String, Object> row = new LinkedHashMap<>();
      row.put("module", name);
      row.put("status", "analyzed");
      row.put("findings", bugs.size());
      row.put("high", high);
      row.put("errors", errors);
      row.put(
          "missingClasses",
          elements(xml, "MissingClass").stream().map(Element::getTextContent).toList());
      row.put("report", root().relativize(report).toString().replace(".xml", ".html"));
      rows.add(row);
    }
    json(root().resolve("target/spotbugs/index.json"), rows);
    StringBuilder html =
        new StringBuilder(
            "<!doctype html><title>Domino Widgets SpotBugs</title><h1>Reactor SpotBugs reports</h1><table><tr><th>Module</th><th>Status</th><th>Findings</th><th>High</th></tr>");
    for (var row : rows)
      html.append("<tr><td><a href=\"../../")
          .append(html(row.getOrDefault("report", "").toString()))
          .append("\">")
          .append(html(row.get("module").toString()))
          .append("</a></td><td>")
          .append(row.get("status"))
          .append("</td><td>")
          .append(row.getOrDefault("findings", ""))
          .append("</td><td>")
          .append(row.getOrDefault("high", ""))
          .append("</td></tr>");
    write(root().resolve("target/spotbugs/index.html"), html + "</table>");
    if (!indexOnly) require(problems.isEmpty(), String.join("\n", problems));
    getLog()
        .info(
            "Indexed "
                + rows.size()
                + " analyzer reports"
                + (indexOnly ? " (diagnostics only)" : "; analysis gate passed"));
  }

  private static String childAttribute(Element parent, String tag, String attr) {
    var children = elements(parent, tag);
    return children.isEmpty() ? null : children.get(0).getAttribute(attr);
  }
}
