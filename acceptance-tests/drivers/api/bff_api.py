import requests
from requests import Response

from drivers.casa_driver import CasaDriver

class BffApi(CasaDriver):
    def deposit(self, account_id: str, amount: int) -> Response:
        url = f"http://localhost:8080/casa/deposit/{account_id}"
        payload = {"amount": amount}
        response = requests.post(url, json=payload)
        response.raise_for_status()
        return response
