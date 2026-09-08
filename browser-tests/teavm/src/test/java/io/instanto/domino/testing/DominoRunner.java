package io.instanto.domino.testing;

import org.teavm.junit.TeaVMTestRunner;

/** Starts JVM fixture hosting, then delegates all browser execution to TeaVM. */
public final class DominoRunner extends TeaVMTestRunner {
  public DominoRunner(Class<?> testClass) throws org.junit.runners.model.InitializationError {
    super(prepare(testClass));
  }

  private static Class<?> prepare(Class<?> testClass) {
    FixtureServer.start();
    return testClass;
  }

  @Override
  public void run(org.junit.runner.notification.RunNotifier notifier) {
    var progress =
        new org.junit.runner.notification.RunListener() {
          @Override
          public void testStarted(org.junit.runner.Description description) {
            System.out.println("Cucumber: " + description.getMethodName());
          }
        };
    notifier.addListener(progress);
    try {
      super.run(notifier);
    } finally {
      notifier.removeListener(progress);
    }
  }
}
