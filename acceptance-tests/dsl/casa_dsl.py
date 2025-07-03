from playwright.sync_api import Locator

from drivers.casa_driver import CasaDriver


class CasaDSL:
    def __init__(self, driver: CasaDriver):
        self.casa = driver

    def visit(self) -> None:
        self.casa.visit()

    def make_a_deposit(self, amount: int) -> None:
        return self.casa.deposit(amount)

    def close(self) -> None:
        self.casa.close()
