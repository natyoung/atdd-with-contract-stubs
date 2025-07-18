from abc import ABC, abstractmethod
from numbers import Number
from typing import Any


class CasaDriver(ABC):
    @abstractmethod
    def setup(self) -> None:
        pass

    @abstractmethod
    def deposit(self, account_id: str, amount: int) -> Any:
        pass

    @abstractmethod
    def balance(self, account_id: str) -> Any:
        pass

    @abstractmethod
    def teardown(self) -> None:
        pass
