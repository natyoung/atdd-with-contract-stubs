from pytest_bdd import given, when, then, parsers, scenarios

scenarios('../features/deposit.feature')


@given(parsers.parse('I have an existing account with ID "{account_id}"'))
def setup_account(request, account_id: str):
    driver = request.getfixturevalue('casa_driver')
    driver.setup()


@when(parsers.parse('I make a deposit of {amount:d} to account "{account_id}"'))
def make_deposit(request, amount: int, account_id: str):
    driver = request.getfixturevalue('casa_driver')
    response = driver.deposit(account_id, amount)
    request.node.stash['response'] = response


@then(parsers.parse('the deposit is successful'))
def verify_deposit_success(request):
    driver = request.getfixturevalue('casa_driver')
    response = request.node.stash.get('response', '')  # Isolated per-scenario stash
    driver.teardown()
    assert response is True
