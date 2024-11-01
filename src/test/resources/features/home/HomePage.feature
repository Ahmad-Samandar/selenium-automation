Feature: Home Page Actions

  Scenario: User clicks on Sign In button
    Given the user is on the home page
    When the user clicks on the Sign In button
    Then the user should be redirected to the login page
