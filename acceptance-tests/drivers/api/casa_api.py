import requests

from drivers.casa_driver import CasaDriver


class CasaApi(CasaDriver):
    BASE_URL = f"http://localhost:8081"

    def setup(self) -> None:
        pass

    def teardown(self) -> None:
        pass

    def deposit(self, account_id: str, amount: int) -> bool:
        payload = {"amount": amount, "accountId": account_id}
        response = requests.post(f"{self.BASE_URL}/deposit", json=payload)
        response.raise_for_status()
        return response.status_code == 200
