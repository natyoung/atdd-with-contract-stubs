from abc import ABC, abstractmethod


class CasaDriver(ABC):
    @abstractmethod
    def initialise(self) -> None:
        pass

    @abstractmethod
    def deposit(self, amount: int):
        pass

    @abstractmethod
    def close(self) -> None:
        pass
