from drivers.casa_driver import CasaDriver


class CasaDSL:
    def __init__(self, driver: CasaDriver):
        self.driver = driver

    def make_a_deposit(self, account_id: str, amount: int):
        return self.driver.deposit(account_id, amount)
