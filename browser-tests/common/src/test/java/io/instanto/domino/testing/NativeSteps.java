package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/native.feature", runner = DominoRunner.class)
public class NativeSteps extends BrowserSteps {
  @Then("storage, dates, locales, promises, Blob fetch and SVG use native browser APIs")
  public void nativeApis() {
    Browser.Element apis = el("#browser-apis");
    attribute(apis, "data-storage", "passed");
    attribute(apis, "data-date-parse", "passed");
    attribute(apis, "data-spanish", "enero");
    check(() -> assertTrue(apis.attr("data-arabic").contains("يناير")));
    attribute(apis, "data-promise", "PROMISE-VALUE");
    attribute(apis, "data-rejection", "rejected-value");
    attribute(apis, "data-blob", "blob-value");
    attribute(apis.first("svg rect"), "fill", "#4466cc");
  }

  @When("I choose a text file for the native file reader")
  public void file() {
    el("#native-upload").upload("sample.txt", "uploaded text");
  }

  @Then("the file reader returns its contents")
  public void readFile() {
    attribute(el("#browser-apis"), "data-file", "uploaded text");
  }

  @When("I push a history entry and go back")
  public void history() {
    el("#push-history").click();
    check(() -> assertTrue(browser.url().endsWith("#detail")));
    browser.back();
  }

  @Then("the native history handler reports back navigation")
  public void back() {
    attribute(el("#browser-apis"), "data-history", "back");
  }
}
