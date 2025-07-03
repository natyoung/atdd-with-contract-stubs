import unittest

from playwright.sync_api import expect

from drivers.web.casa_page import CasaPage
from dsl.casa_dsl import CasaDSL


class TestCasa(unittest.TestCase):

    def setUp(self):
        driver = CasaPage()
        self.casa = CasaDSL(driver)

    def test_add_funds(self):
        self.casa.visit()
        self.casa.make_a_deposit(1)
        expect(self.casa.balance()).to_contain_text("1")

    def tearDown(self):
        self.casa.close()
