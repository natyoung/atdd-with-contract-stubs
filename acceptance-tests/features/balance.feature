Feature: Deposit into CASA account
  As a CASA account holder
  I want to know my balance
  So that I can make financial decisions

  Scenario: Check my balance
    Given I have an existing account with ID "1"
    When I request the balance for account ID "1"
    Then I am provided with my account balance
