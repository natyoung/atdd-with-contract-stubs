import requests

from drivers.casa_driver import CasaDriver
from drivers.infrastructure.psql_db_connector import PsqlDbConnector


class CasaApi(CasaDriver):
    BASE_URL = f"http://localhost:8081"

    def setup(self) -> None:
        connector = PsqlDbConnector()
        try:
            connector.connect()
            connector.execute("DELETE FROM CasaAccount")
            connector.execute("INSERT INTO CasaAccount (id, accountId, balance) VALUES (%s, %s, %s)", (1, "1", 1,))
        finally:
            connector.close()

    def teardown(self) -> None:
        connector = PsqlDbConnector()
        connector.connect()
        connector.execute("DELETE FROM CasaAccount")

    def deposit(self, account_id: str, amount: int) -> bool:
        payload = {"amount": amount, "accountId": account_id}
        response = requests.post(f"{self.BASE_URL}/deposit", json=payload)
        response.raise_for_status()
        return response.status_code == 200

    def balance(self, account_id: str) -> bool:
        response = requests.get(f"{self.BASE_URL}/balance/{account_id}")
        response.raise_for_status()
        return response.status_code == 200 and response.json()["balance"] == 1
