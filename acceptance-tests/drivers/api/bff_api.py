import requests

from drivers.casa_driver import CasaDriver


class BffApi(CasaDriver):
    def setup(self) -> None:
        pass

    def teardown(self) -> None:
        pass

    def deposit(self, account_id: str, amount: int) -> bool:
        url = f"http://localhost:8080/casa/deposit/{account_id}"
        payload = {"amount": amount}
        response = requests.post(url, json=payload)
        response.raise_for_status()
        return response.status_code == 200
