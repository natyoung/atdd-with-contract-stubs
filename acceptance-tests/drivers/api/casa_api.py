import requests

from drivers.casa_driver import CasaDriver
from drivers.infrastructure.h2_db_connector import H2DBConnector


class CasaApi(CasaDriver):
    BASE_URL = f"http://localhost:8081"

    def setup(self) -> None:
        connector = H2DBConnector(dbname="mem:devdb", port=5435)
        try:
            connector.connect()
            connector.execute("DELETE FROM CasaAccount")
            connector.execute("INSERT INTO CasaAccount (id, accountId, balance) VALUES (%s, %s, %s)", (1, "1", 1,))
        finally:
            connector.close()

    def teardown(self) -> None:
        pass

    def deposit(self, account_id: str, amount: int) -> bool:
        payload = {"amount": amount, "accountId": account_id}
        response = requests.post(f"{self.BASE_URL}/deposit", json=payload)
        response.raise_for_status()
        return response.status_code == 200
