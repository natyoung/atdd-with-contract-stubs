import unittest

from playwright.sync_api import sync_playwright

from drivers.api.bff_api import BffApi
from drivers.api.casa_api import CasaApi
from drivers.web.casa_page import CasaPage
from dsl.casa_dsl import CasaDSL

# TODO: Move drivers to the separate repos/pipelines, import DSL & scenarios, automate stub management for local
class TestCasa(unittest.TestCase):

    def setUp(self):
        self.playwright = sync_playwright().start()
        self.driver_web = CasaPage(self.playwright)
        self.driver_bff_api = BffApi()
        self.driver_casa_api = CasaApi()

    def test_make_a_deposit_to_existing_account(self):
        dsl = CasaDSL(self.driver_web)
        dsl.make_a_deposit("1", 1)

        dsl = CasaDSL(self.driver_bff_api)
        response = dsl.make_a_deposit("1", 1)
        self.assertEqual(response.json()["result"], "success")

        dsl = CasaDSL(self.driver_casa_api)
        response = dsl.make_a_deposit("1", 1)
        self.assertEqual(response.json()["result"], "success")

    def tearDown(self):
        self.playwright.stop()
