@skip-jvm
Feature: Original widget contracts
  Background:
    Given the Domino showcase page "contracts" is open

  Scenario: Button handlers survive reattachment and can be removed
    When I click the counter
    Then the counter reads "Count 1"
    When I reattach the button 3 times
    And I click the counter
    Then the counter reads "Count 2"
    When I remove the counter handler
    And I click the counter
    Then the counter reads "Count 2"

  Scenario: Required input validation
    When I validate the form
    Then the name is invalid
    When I enter the name "Ada"
    And I validate the form
    Then the name is valid

  Scenario: Dialogs open and close repeatedly
    When I open and close the dialog 3 times
    Then the dialog is closed

  Scenario: Tables render and replace records
    Then the table contains "Alpha"
    And the table contains "Beta"
    When I replace the records
    Then the table contains "Gamma"
    And the table does not contain "Alpha"

  Scenario: Inputs emit value changes
    When I enter the name "Grace"
    And I validate the form
    Then the value change reports "Grace"

  Scenario: Tables support selection and filtering
    When I select the first record
    Then the first record is selected
    When I filter the records
    Then only Alpha is visible
    When I clear the record filter
    Then Beta is visible again

  Scenario: Escape dismisses a dialog
    When I open the dialog and press Escape
    Then the dialog is closed

  Scenario: All four widgets remain usable after repeated screen attachment
    When I reattach the screen 3 times
    And I click the counter
    Then the counter reads "Count 1"
    When I enter the name "Reattached"
    And I validate the form
    Then the value change reports "Reattached"
    When I open the dialog using the keyboard and close it
    Then focus returns to the dialog opener
    When I replace the records
    Then the table contains "Gamma"

  Scenario: Original button examples show sizes, disabled states and groups
    Given the Domino showcase page "buttons" is open
    Then button sizes, disabled buttons and groups are displayed

  Scenario: Original form examples accept text and show a textarea
    Given the Domino showcase page "forms" is open
    When I type "Shared example" in the first editable example
    Then the example value is "Shared example" and a textarea is visible

  Scenario: Original message dialogs open and dismiss
    Given the Domino showcase page "dialogs" is open
    When I open the example message dialog
    And I acknowledge the message
    Then the example message is hidden
