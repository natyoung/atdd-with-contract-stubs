import unittest

from playwright.sync_api import sync_playwright

from drivers.web.casa_page import CasaPage
from dsl.casa_dsl import CasaDSL


class TestCasa(unittest.TestCase):

    def setUp(self):
        self.playwright = sync_playwright().start()
        driver = CasaPage(self.playwright)
        self.dsl = CasaDSL(driver)

    def test_make_a_deposit_to_existing_account(self):
        self.dsl.make_a_deposit(1)

    def tearDown(self):
        self.playwright.stop()
