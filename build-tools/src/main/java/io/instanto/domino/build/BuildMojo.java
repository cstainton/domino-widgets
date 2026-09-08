package io.instanto.domino.build;

import java.io.File;
import java.nio.file.Path;
import org.apache.maven.plugin.*;
import org.apache.maven.plugins.annotations.Parameter;

/** Shared configuration for the repository's build goals. */
public abstract class BuildMojo extends AbstractMojo {
  /** Repository root; configured by each lifecycle module. */
  @Parameter(
      property = "domino.root",
      defaultValue = "${maven.multiModuleProjectDirectory}",
      required = true)
  private File rootDirectory;

  protected final Path root() {
    return rootDirectory.toPath().toAbsolutePath().normalize();
  }

  @Override
  public final void execute() throws MojoExecutionException {
    try {
      run();
    } catch (Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  protected abstract void run() throws Exception;
}
