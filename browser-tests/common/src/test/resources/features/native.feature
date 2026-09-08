@skip-jvm
Feature: Native browser APIs
  Scenario: Native values, asynchronous APIs, files and history work together
    Given the Domino showcase page "browser-apis" is open
    Then storage, dates, locales, promises, Blob fetch and SVG use native browser APIs
    When I choose a text file for the native file reader
    Then the file reader returns its contents
    When I push a history entry and go back
    Then the native history handler reports back navigation
