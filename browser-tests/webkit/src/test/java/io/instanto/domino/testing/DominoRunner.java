package io.instanto.domino.testing;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/** Runs the same generated browser scenarios on the JVM through Playwright WebKit. */
public final class DominoRunner extends BlockJUnit4ClassRunner {
  public DominoRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    FixtureServer.start();
  }
}
