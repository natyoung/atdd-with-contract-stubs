from numbers import Number

from pytest_bdd import given, when, then, parsers, scenarios

scenarios('../features/balance.feature')


@given(parsers.parse('I have an existing account with ID "{account_id}"'))
def setup_account(request, account_id: str):
    driver = request.getfixturevalue('casa_driver')
    driver.setup()


@when(parsers.parse('I request the balance for account ID "{account_id}"'))
def get_balance(request, account_id: str):
    driver = request.getfixturevalue('casa_driver')
    balance = driver.balance(account_id)
    request.node.stash['balance'] = balance


@then(parsers.parse('I am provided with my account balance'))
def verify_balance(request):
    driver = request.getfixturevalue('casa_driver')
    result = request.node.stash.get('balance', '')
    driver.teardown()
    assert result is True
