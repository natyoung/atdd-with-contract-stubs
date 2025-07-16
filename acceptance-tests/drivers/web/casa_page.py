import os
from playwright.sync_api import expect

from drivers.casa_driver import CasaDriver

class CasaPage(CasaDriver):
    def setup(self) -> None:
        pass

    def teardown(self) -> None:
        pass

    BASE_URL: str = 'http://localhost:3000'
    STATE_FILE: str = 'playwright/.auth/state.json'

    def __init__(self, playwright) -> None:
        self.playwright = playwright
        self.browser = self.playwright.chromium.launch(headless=False)
        os.makedirs(os.path.dirname(self.STATE_FILE), exist_ok=True)
        if os.path.exists(self.STATE_FILE):
            self.context = self.browser.new_context(storage_state=self.STATE_FILE)
        else:
            self.context = self.browser.new_context()
        self.page = self.context.new_page()

    def deposit(self, account_id: str, amount: int) -> bool:
        if not os.path.exists(self.STATE_FILE):
            self.page.goto(f"{self.BASE_URL}/login")
            self.page.get_by_test_id('accountId').fill("1")
            self.page.get_by_test_id('password').fill("password")
            self.page.get_by_test_id('submit-login').click()
            self.page.wait_for_selector('[data-testid="success"]', timeout=10000)
            self.context.storage_state(path=self.STATE_FILE)

        self.page.goto(f"{self.BASE_URL}/deposit")
        self.page.get_by_test_id('amount').fill(str(amount))
        self.page.get_by_test_id('submit-amount').click()
        try:
            expect(self.page.get_by_test_id('result')).to_have_text("success")
            return True
        except AssertionError:
            return False
        finally:
            self.context.close()
            self.browser.close()
