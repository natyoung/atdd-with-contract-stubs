from abc import ABC, abstractmethod
from typing import Any


class CasaDriver(ABC):
    @abstractmethod
    def deposit(self, account_id: str, amount: int) -> Any:
        pass
