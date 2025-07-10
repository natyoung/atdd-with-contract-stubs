from playwright.sync_api import expect

from drivers.casa_driver import CasaDriver


class CasaPage(CasaDriver):
    URL: str = 'http://localhost:3000'

    def __init__(self, playwright) -> None:
        self.playwright = playwright
        self.browser = self.playwright.chromium.launch(headless=False)
        self.context = self.browser.new_context()
        self.page = self.context.new_page()

    def deposit(self, account_id: str, amount: int) -> bool:
        self.page.goto(f"{self.URL}/deposit") # {account_id when auth is set up}
        self.page.get_by_test_id('amount').type(str(amount))
        self.page.get_by_test_id('submit-amount').click()
        expect(self.page.get_by_test_id('result')).to_have_text("success")
        self.close()

    def close(self):
        self.context.close()
        self.browser.close()
