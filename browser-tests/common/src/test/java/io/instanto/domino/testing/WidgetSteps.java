package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(
    value = {"features/contracts.feature", "features/interactions.feature"},
    runner = DominoRunner.class)
public class WidgetSteps extends BrowserSteps {
  private Browser.Element selected;
  private Browser.Element secondary;
  private String previous;
  private int before;

  @When("I click the counter")
  public void count() {
    el("#count").click();
  }

  @When("I reattach the button {int} times")
  public void reattachButton(int times) {
    for (int i = 0; i < times; i++) el("#reattach").click();
  }

  @When("I remove the counter handler")
  public void removeHandler() {
    el("#remove-handler").click();
  }

  @Then("the counter reads {string}")
  public void counter(String text) {
    check(() -> assertEquals(text, el("#count").text()));
  }

  @When("I validate the form")
  public void validate() {
    el("#validate").click();
  }

  @When("I enter the name {string}")
  public void name(String name) {
    el("#name-field input").fill(name);
  }

  @Then("the name is invalid")
  public void invalid() {
    check(() -> assertTrue(el("#name-field").attr("class").contains("dui-field-invalid")));
  }

  @Then("the name is valid")
  public void valid() {
    check(() -> assertFalse(el("#name-field").attr("class").contains("dui-field-invalid")));
  }

  @Then("the value change reports {string}")
  public void valueChange(String name) {
    attribute(el("#screen"), "data-value", name);
  }

  @When("I open and close the dialog {int} times")
  public void dialogs(int times) {
    for (int i = 0; i < times; i++) {
      el("#open-dialog").click();
      visible(el("#close-dialog"), true);
      el("#close-dialog").click();
      dialogClosed();
    }
  }

  @Then("the dialog is closed")
  public void dialogClosed() {
    check(
        () -> assertTrue(root().all("#close-dialog").stream().noneMatch(Browser.Element::visible)));
  }

  @Then("the table contains {string}")
  public void tableContains(String text) {
    contains(el("#records"), text);
  }

  @Then("the table does not contain {string}")
  public void tableExcludes(String text) {
    check(() -> assertFalse(el("#records").text().contains(text)));
  }

  @When("I replace the records")
  public void updateRows() {
    el("#update-rows").click();
  }

  @When("I select the first record")
  public void selectFirst() {
    el("#select-first").click();
  }

  @Then("the first record is selected")
  public void selectedRecord() {
    attribute(el("#screen"), "data-selected", "true");
  }

  @When("I filter the records")
  public void filterRows() {
    el("#filter-rows").click();
  }

  @Then("only Alpha is visible")
  public void filtered() {
    visible(withText(el("#records"), "tbody tr", "Alpha"), true);
    check(
        () ->
            assertTrue(
                el("#records").all("tbody tr").stream()
                    .noneMatch(e -> e.text().contains("Beta") && e.visible())));
  }

  @When("I clear the record filter")
  public void clearFilter() {
    el("#clear-filter").click();
  }

  @Then("Beta is visible again")
  public void unfiltered() {
    check(() -> assertTrue(withText(el("#records"), "tbody tr", "Beta").visible()));
  }

  @When("I open the dialog and press Escape")
  public void escapeDialog() {
    el("#open-dialog").click();
    visible(el("#close-dialog"), true);
    el("#close-dialog").focus();
    el("#close-dialog").press("Escape");
  }

  @When("I reattach the screen {int} times")
  public void reattachScreen(int times) {
    for (int i = 0; i < times; i++) el("#reattach-screen").click();
  }

  @When("I open the dialog using the keyboard and close it")
  public void keyboardDialog() {
    el("#open-dialog").focus();
    check(() -> assertTrue(el("#open-dialog").focused()));
    el("#open-dialog").press("Enter");
    visible(el("#close-dialog"), true);
    el("#close-dialog").click();
  }

  @Then("focus returns to the dialog opener")
  public void focusReturned() {
    check(() -> assertTrue(el("#open-dialog").focused()));
  }

  @Then("button sizes, disabled buttons and groups are displayed")
  public void buttonExamples() {
    contains(el("#gallery-examples"), "BUTTON SIZES");
    contains(el("#gallery-examples"), "DISABLED BUTTONS");
    visible(el("#gallery-examples button"), true);
  }

  @When("I type {string} in the first editable example")
  public void fillExample(String text) {
    selected = el("#gallery-examples input:not([disabled]):not([readonly])");
    selected.fill(text);
  }

  @Then("the example value is {string} and a textarea is visible")
  public void formExamples(String text) {
    check(() -> assertEquals(text, selected.value()));
    visible(el("#gallery-examples textarea"), true);
  }

  @When("I open the example message dialog")
  public void messageDialog() {
    root().text("CLICK ME", false).click();
    check(
        () -> {
          selected = root().text("You have just opened a message dialog.", false);
          assertTrue(selected.visible());
        });
  }

  @When("I acknowledge the message")
  public void acknowledge() {
    withText(root(), "button", "Ok").click();
  }

  @Then("the example message is hidden")
  public void messageHidden() {
    visible(selected, false);
  }

  @When("I remove the first chip")
  public void removeChip() {
    before = root().all(".dui-chip").size();
    el(".dui-chip-remove").click();
  }

  @Then("one fewer chip remains")
  public void fewerChips() {
    check(() -> assertEquals(before - 1, root().all(".dui-chip").size()));
  }

  @When("I switch from Home to Settings")
  public void switchTabs() {
    selected = el(".dui-card");
    selected.text("HOME", false).click();
    visible(selected.text("Home Content", false), true);
    selected.text("SETTINGS", false).click();
  }

  @Then("Settings is visible and Home is hidden")
  public void tabsVisible() {
    visible(selected.text("Settings Content", false), true);
    visible(selected.text("Home Content", false), false);
  }

  @When("I select day fifteen and advance one month")
  public void calendarNext() {
    selected = el(".dui-calendar");
    selected.all(".dui-calendar-day-number").stream()
        .filter(e -> e.text().trim().equals("15"))
        .findFirst()
        .orElseThrow()
        .click();
    contains(selected.first(".dui-selected-date"), "15");
    secondary = selected.first(".dui-calendar-selectors-month");
    previous = secondary.text();
    selected.first(".dui-calendar-selectors-next").click();
    check(() -> assertNotEquals(previous, secondary.text()));
  }

  @Then("returning one month restores the calendar heading")
  public void calendarPrevious() {
    selected.first(".dui-calendar-selectors-previous").click();
    check(() -> assertEquals(previous, secondary.text()));
  }

  @When("I enter the number {string} and leave the field")
  public void number(String value) {
    selected = el("#gallery-examples input");
    selected.fill(value);
    selected.press("Tab");
  }

  @Then("the numeric value is {string}")
  public void numberValue(String value) {
    check(() -> assertEquals(value, selected.value()));
  }

  @When("I select Bob's table row")
  public void bob() {
    selected = el("table");
    contains(selected, "Alice Example");
    secondary = withText(selected, "tbody tr", "Bob Example");
    secondary.click();
  }

  @Then("the row is selected and another click clears it")
  public void bobToggle() {
    check(() -> assertTrue(secondary.attr("class").contains("dui-datatable-row-selected")));
    secondary.click();
    check(() -> assertFalse(secondary.attr("class").contains("dui-datatable-row-selected")));
  }

  @When("I move to the second table page")
  public void pageTwo() {
    selected = el(".dui-card");
    contains(selected, "Alice Example");
    selected.text("2", true).click();
    check(() -> assertFalse(selected.text().contains("Alice Example")));
  }

  @Then("returning to the first page restores Alice")
  public void pageOne() {
    selected.text("1", true).click();
    contains(selected, "Alice Example");
  }

  @When("I expand the Computer tree node")
  public void expandTree() {
    selected =
        root().all(".dui-tree").stream()
            .filter(e -> !e.all(".mdi-desktop-classic").isEmpty())
            .findFirst()
            .orElseThrow()
            .text("Computer", false)
            .closest("li");
    secondary = selected.first(":scope > ul");
    attribute(secondary, "dui-collapsed", "true");
    selected.first(":scope > a i").click();
    check(() -> assertNotEquals("true", secondary.attr("dui-collapsed")));
    check(() -> assertNotEquals("0px", secondary.css("height")));
    secondary.finishAnimations();
  }

  @Then("collapsing it again hides its children")
  public void collapseTree() {
    selected.first(":scope > a i").click();
    attribute(secondary, "dui-collapsed", "true");
    check(() -> assertEquals("0px", secondary.css("height")));
  }

  @When("I replace the rich text with {string}")
  public void editRichText(String value) {
    selected = el("#richtext-example");
    secondary = selected.first("[contenteditable=true]");
    contains(secondary, "Initial content");
    secondary.fill(value);
    el("#read-html").click();
  }

  @Then("the returned HTML contains {string}")
  public void readRichText(String value) {
    check(() -> assertTrue(selected.attr("data-html").contains(value)));
  }

  @Then("resetting the editor restores Reset content")
  public void resetRichText() {
    el("#reset-editor").click();
    contains(secondary, "Reset content");
  }

  @When("I upload a text file through the original upload widget")
  public void upload() {
    selected = root().all(".dui-file-upload").get(1);
    selected.first("input[type=file]").upload("contract.txt", "domino upload contract");
  }

  @Then("the server receives multipart content and the widget reports success")
  public void uploaded() {
    contains(selected, "Upload completed.");
    String request = browser.uploadedRequest();
    assertTrue(request, request.startsWith("POST\n"));
    assertTrue(request, request.contains("multipart/form-data"));
    assertTrue(request, request.contains("domino upload contract"));
  }

  @When("I search for United in the country suggestions")
  public void suggest() {
    selected = withText(root(), ".dui-form-field", "Suggested country").first("input");
    selected.fill("United");
  }

  @Then("I can choose United Kingdom")
  public void chooseCountry() {
    check(() -> assertTrue(root().text("United Kingdom", true).visible()));
    root().text("United Kingdom", true).click();
    check(() -> assertEquals("United Kingdom", selected.value()));
  }
}
