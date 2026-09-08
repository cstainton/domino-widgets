package io.instanto.compat;

import java.nio.file.*;
import java.util.*;

/** Selects only Domino widget sources for the shared JsInterop transformer. */
public final class GenerateDomino {
  private static void write(Path path, String source) throws Exception {
    Files.createDirectories(path.getParent());
    Files.writeString(path, GenerateBindings.transform(source));
  }

  public static void main(String[] args) throws Exception {
    Path root = Path.of(args[0]);
    Path out = root.resolve("target/compat");
    if (Files.exists(out))
      try (var files = Files.walk(out)) {
        for (Path p : files.sorted(Comparator.reverseOrder()).toList()) Files.delete(p);
      }
    Path sourceRoot = root.resolve("target/intake/java");
    try (var files = Files.walk(sourceRoot)) {
      for (Path p :
          files
              .filter(
                  p ->
                      p.toString().endsWith(".java")
                          && !sourceRoot.relativize(p).toString().startsWith("org/gwtproject/"))
              .sorted()
              .toList())
        write(
            out.resolve(
                    sourceRoot.relativize(p).toString().startsWith("org/gwtproject/")
                        ? "services"
                        : "domino")
                .resolve(sourceRoot.relativize(p)),
            Files.readString(p));
    }
    StringBuilder manifest = new StringBuilder();
    var digest = java.security.MessageDigest.getInstance("SHA-256");
    try (var files = Files.walk(out)) {
      for (Path p : files.filter(p -> p.toString().endsWith(".java")).sorted().toList()) {
        manifest
            .append(java.util.HexFormat.of().formatHex(digest.digest(Files.readAllBytes(p))))
            .append("  ")
            .append(out.relativize(p))
            .append("\n");
      }
    }
    Files.writeString(out.resolve("sources.sha256"), manifest);
    System.out.println("Generated TeaVM bindings and Domino sources in " + out);
  }
}
