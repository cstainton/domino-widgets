@skip-jvm
Feature: Original showcase interactions
  Scenario: Removing a chip changes the widget DOM
    Given the Domino showcase page "chips" is open
    When I remove the first chip
    Then one fewer chip remains

  Scenario: Tabs switch visible content
    Given the Domino showcase page "tabs" is open
    When I switch from Home to Settings
    Then Settings is visible and Home is hidden

  Scenario: Calendars select a day and navigate months
    Given the Domino showcase page "datepicker" is open
    When I select day fifteen and advance one month
    Then returning one month restores the calendar heading

  Scenario: Numeric fields accept changed values
    Given the Domino showcase page "inputfields" is open
    When I enter the number "42" and leave the field
    Then the numeric value is "42"

  Scenario: Original table rows can be selected and deselected
    Given the Domino showcase page "table-selection-plugin" is open
    When I select Bob's table row
    Then the row is selected and another click clears it

  Scenario: Original table pagination changes records
    Given the Domino showcase page "table-pagination-plugin" is open
    When I move to the second table page
    Then returning to the first page restores Alice

  Scenario: Trees expand and collapse nested content
    Given the Domino showcase page "tree" is open
    When I expand the Computer tree node
    Then collapsing it again hides its children

  Scenario: Rich text can be edited, read as HTML and reset
    Given the Domino showcase page "richtext" is open
    When I replace the rich text with "Edited content"
    Then the returned HTML contains "Edited content"
    And resetting the editor restores Reset content

  Scenario: Upload widgets send real multipart data
    Given the Domino showcase page "advanced-forms" is open
    When I upload a text file through the original upload widget
    Then the server receives multipart content and the widget reports success

  Scenario: Dynamic suggestions fetch and select a country
    Given the Domino showcase page "advanced-forms" is open
    When I search for United in the country suggestions
    Then I can choose United Kingdom
