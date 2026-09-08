package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/reuse.feature", runner = DominoRunner.class)
public class ReuseSteps extends BrowserSteps {
  @Given("the independent logger application is open")
  public void openLogger() {
    openApplication("reuse", "");
  }

  @Then("the published logger works without widget dependencies")
  public void logger() {
    attribute(root(), "data-reuse", "ready");
    check(() -> assertTrue(browser.console().contains("compat-reuse-success")));
  }
}
