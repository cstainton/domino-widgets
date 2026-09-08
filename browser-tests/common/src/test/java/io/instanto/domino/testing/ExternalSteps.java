package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/external.feature", runner = DominoRunner.class)
public class ExternalSteps extends BrowserSteps {
  @Given("the external BOM consumer is open")
  public void openExternal() {
    openApplication("external-teavm", "");
    attribute(root(), "data-ready", "true");
  }

  @When("I greet Independent app")
  public void greet() {
    el("#name input").fill("Independent app");
    el("#greet").click();
  }

  @Then("the external app handles input and loads matching calendar assets")
  public void rendered() {
    attribute(root(), "data-greeting", "Hello Independent app");
    visible(el(".dui-calendar"), true);
    assertTrue(browser.stylesheetAvailable());
  }
}
