from playwright.sync_api import expect

from drivers.casa_driver import CasaDriver


class CasaApi(CasaDriver):
    URL: str = 'http://localhost:8080/deposit'

    def __init__(self, playwright) -> None:
        self.playwright = playwright
        self.browser = self.playwright.chromium.launch(headless=False)
        self.context = self.browser.new_context()
        self.page = self.context.new_page()

    def initialise(self) -> None:
        self.page.goto(f"{self.URL}")

    def deposit(self, amount: int):
        self.page.get_by_test_id('amount').type(str(amount))
        self.page.get_by_test_id('submit-amount').click()
        return expect(self.page.get_by_test_id('result')).to_have_text("success")

    def close(self):
        self.context.close()
        self.browser.close()
