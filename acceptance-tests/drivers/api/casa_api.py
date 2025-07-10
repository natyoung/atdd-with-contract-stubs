import requests
from requests import Response

from drivers.casa_driver import CasaDriver

class CasaApi(CasaDriver):
    def deposit(self, account_id: str, amount: int) -> Response:
        url = f"http://localhost:8081/deposit"
        payload = {"amount": amount, "accountId": 1}
        response = requests.post(url, json=payload)
        response.raise_for_status()
        return response

    def setup(self):
        pass
        # create new account {account_id}

    def teardown(self):
        pass
        # clear data