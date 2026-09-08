package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.AfterScenario;
import io.instanto.cucumber.tea.Given;

/** Shared scenario lifecycle; backend drivers contain only browser mechanics. */
public abstract class BrowserSteps {
  protected Browser browser;

  protected Browser.Element root() {
    return browser.root();
  }

  protected Browser.Element el(String selector) {
    return root().first(selector);
  }

  protected void check(Runnable assertion) {
    browser.waitFor(assertion);
  }

  protected void contains(Browser.Element element, String text) {
    check(
        () ->
            assertTrue(
                "Expected " + text + " in " + element.text(), element.text().contains(text)));
  }

  protected void attribute(Browser.Element element, String name, String value) {
    check(() -> assertEquals(value, element.attr(name)));
  }

  protected void visible(Browser.Element element, boolean expected) {
    check(() -> assertEquals("Visibility", expected, element.visible()));
  }

  protected Browser.Element withText(Browser.Element parent, String selector, String text) {
    return parent.all(selector).stream()
        .filter(e -> e.text().contains(text))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Missing " + text));
  }

  protected void openApplication(String application, String route) {
    if (browser == null) browser = new BrowserDriver();
    browser.open(application, route);
  }

  @Given("the Domino showcase page {string} is open")
  public void open(String route) {
    openApplication("teavm", route);
    String selector =
        switch (route) {
          case "contracts" -> "#screen";
          case "richtext" -> "#richtext-example";
          case "browser-apis" -> "#browser-apis";
          default -> "#gallery-examples";
        };
    check(() -> assertFalse("Fixture did not mount", root().all(selector).isEmpty()));
    if (!route.equals("browser-apis")) attribute(el(selector), "data-ready", "true");
  }

  @AfterScenario
  public void healthyAndClose() {
    if (browser != null) {
      try {
        browser.settle();
        assertEquals("Browser diagnostics", "", browser.errors());
      } finally {
        browser.close();
      }
    }
  }
}
