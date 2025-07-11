import pytest
from playwright.sync_api import sync_playwright
from drivers.web.casa_page import CasaPage
from drivers.api.bff_api import BffApi
from drivers.api.casa_api import CasaApi

def pytest_addoption(parser):
    parser.addoption(
        "--driver",
        action="store",
        default="web",
        help="Driver type: web, bff_api, or casa_api"
    )

@pytest.fixture(scope="function")
def casa_driver(request):
    driver_type = request.config.getoption("--driver")

    if driver_type == "web":
        playwright = sync_playwright().start()
        yield CasaPage(playwright)
        playwright.stop()
    elif driver_type == "bff_api":
        yield BffApi()
    elif driver_type == "casa_api":
        yield CasaApi()
    else:
        raise ValueError(f"Invalid driver: {driver_type}. Use web, bff_api, or casa_api.")
