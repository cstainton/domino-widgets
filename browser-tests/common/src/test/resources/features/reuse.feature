@skip-jvm
Feature: Independent compatibility reuse
  Scenario: A published Elemental2 logger works without widget dependencies
    Given the independent logger application is open
    Then the published logger works without widget dependencies
