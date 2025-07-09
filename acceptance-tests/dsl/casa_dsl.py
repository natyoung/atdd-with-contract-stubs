from drivers.casa_driver import CasaDriver


class CasaDSL:
    def __init__(self, driver: CasaDriver):
        self.driver = driver

    def make_a_deposit(self, amount: int):
        self.driver.initialise()
        self.driver.deposit(amount)
        self.driver.close()
