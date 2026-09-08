package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipFile;
import org.apache.maven.plugins.annotations.*;

/** Reject duplicate classes, bundled assets and modified generated sources. */
@Mojo(name = "check-artifacts", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public final class ArtifactsMojo extends BuildMojo {
  @Override
  protected void run() throws Exception {
    Path target = root().resolve("domino-widgets-teavm/target");
    List<Path> jars = new ArrayList<>();
    try (var paths = Files.list(target)) {
      jars.addAll(
          paths
              .filter(
                  p ->
                      p.getFileName().toString().matches("domino-widgets-teavm-.*\\.jar")
                          && !p.toString().endsWith("-sources.jar")
                          && !p.toString().endsWith("-javadoc.jar"))
              .sorted()
              .toList());
    }
    jars.addAll(
        files(target.resolve("compat-dependencies")).stream()
            .filter(p -> p.toString().endsWith(".jar"))
            .toList());
    require(
        jars.size() == 4,
        "Expected widgets plus three resolved compatibility JARs; got " + jars.size());
    int count = checkJars(jars);
    Path manifest = root().resolve("target/compat/sources.sha256");
    require(Files.isRegularFile(manifest), "Missing generated-source manifest");
    for (String line : Files.readAllLines(manifest)) {
      String[] entry = line.split("  ", 2);
      require(entry.length == 2, "Malformed generated-source manifest");
      verifyHash(within(manifest.getParent(), entry[1]), entry[0]);
    }
    getLog()
        .info(
            count
                + " unique classes; code artifacts contain no assets; generated-source hashes match");
  }

  static int checkJars(List<Path> jars) throws Exception {
    Map<String, Path> owners = new HashMap<>();
    for (Path path : jars)
      try (ZipFile jar = new ZipFile(path.toFile())) {
        for (var entry : Collections.list(jar.entries())) {
          String name = entry.getName();
          if (name.endsWith(".class")) {
            Path previous = owners.putIfAbsent(name, path);
            require(previous == null, "Duplicate class " + name + ": " + previous + " and " + path);
          }
          require(
              !name.matches(".*\\.(css|woff|woff2|ttf|eot)$"),
              "Asset duplicated in code artifact: " + path + "/" + name);
        }
      }
    return owners.size();
  }
}
