@skip-jvm
Feature: Published Maven consumer
  Scenario: An external BOM consumer handles input and loads matched assets
    Given the external BOM consumer is open
    When I greet Independent app
    Then the external app handles input and loads matching calendar assets
