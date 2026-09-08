package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/gallery.feature", runner = DominoRunner.class)
public class GallerySteps extends BrowserSteps {
  @Then("the original example renders with loaded images and no browser errors")
  public void renders() {
    assertFalse(el("#gallery-examples").text().isBlank());
    browser.settle();
    check(() -> assertTrue("Gallery images did not load", el("#gallery-examples").imagesLoaded()));
    assertEquals("", browser.errors());
  }
}
