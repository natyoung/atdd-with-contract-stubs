Feature: Deposit into CASA account
  As a Current Account and Savings Account (CASA) account holder
  I want to deposit money into my account
  So that I can earn interest

  Scenario: Make a deposit to an existing account
    Given I have an existing account with ID "1"
    When I make a deposit of 1 to account "1"
    Then the deposit is successful
