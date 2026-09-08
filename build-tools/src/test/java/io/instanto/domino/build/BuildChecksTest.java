package io.instanto.domino.build;

import static org.junit.Assert.*;

import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

public class BuildChecksTest {
  @Rule public TemporaryFolder temporary = new TemporaryFolder();

  @Test
  public void archivePathsCannotEscapeTheOutput() throws Exception {
    Path root = temporary.getRoot().toPath();
    assertThrows(java.io.IOException.class, () -> BuildFiles.within(root, "../outside.java"));
    assertEquals(
        root.resolve("org/example/Sample.java"),
        BuildFiles.within(root, "org/example/Sample.java"));
  }

  @Test
  public void modifiedSourceFailsChecksumVerification() throws Exception {
    Path source = temporary.newFile().toPath();
    Files.writeString(source, "original");
    String digest = BuildFiles.hash(Files.readAllBytes(source));
    BuildFiles.verifyHash(source, digest);
    Files.writeString(source, "modified");
    assertThrows(java.io.IOException.class, () -> BuildFiles.verifyHash(source, digest));
  }

  @Test
  public void methodExtractionIgnoresBracesInStringsAndComments() throws Exception {
    String method =
        "    private void sample() { String text = \"}\"; /* { */ if (true) { run(); } // }\n    }";
    assertEquals(method, ShowcaseMojo.method("class Sample {\n" + method + "\n}", "sample"));
    assertThrows(java.io.IOException.class, () -> ShowcaseMojo.method(method, "missing"));
    assertThrows(
        java.io.IOException.class,
        () -> ShowcaseMojo.method("    private void sample() {", "sample"));
  }

  @Test
  public void codeArtifactsRejectDuplicateClassesAndAssets() throws Exception {
    Path first = jar("first.jar", "sample/Widget.class");
    Path second = jar("second.jar", "sample/Widget.class");
    assertEquals(1, ArtifactsMojo.checkJars(List.of(first)));
    assertThrows(java.io.IOException.class, () -> ArtifactsMojo.checkJars(List.of(first, second)));
    Path assets = jar("assets.jar", "fonts/widget.woff2");
    assertThrows(java.io.IOException.class, () -> ArtifactsMojo.checkJars(List.of(assets)));
  }

  @Test
  public void reportsCannotHideSkippedOrMissingTestcases() throws Exception {
    Path report = temporary.newFile().toPath();
    String prefix = "<testsuite tests=\"1\" failures=\"0\" errors=\"0\" skipped=\"0\">";
    Files.writeString(report, prefix + "<testcase name=\"scenario\"/></testsuite>");
    ResultsMojo.validateSuite(BuildFiles.xml(report), 1, "sample");
    Files.writeString(report, prefix + "</testsuite>");
    assertThrows(
        java.io.IOException.class,
        () -> ResultsMojo.validateSuite(BuildFiles.xml(report), 1, "sample"));
    Files.writeString(report, prefix + "<testcase><skipped/></testcase></testsuite>");
    assertThrows(
        java.io.IOException.class,
        () -> ResultsMojo.validateSuite(BuildFiles.xml(report), 1, "sample"));
  }

  private Path jar(String name, String entry) throws Exception {
    Path path = temporary.getRoot().toPath().resolve(name);
    try (var jar = new ZipOutputStream(Files.newOutputStream(path))) {
      jar.putNextEntry(new ZipEntry(entry));
      jar.write(new byte[] {0});
      jar.closeEntry();
    }
    return path;
  }
}
