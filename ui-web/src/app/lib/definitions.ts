export class DepositResponse {
  result: string;

  constructor(result: string) {
    this.result = result;
  }
}

export class BalanceResponse {
  balance: number;

  constructor(balance: number) {
    this.balance = balance;
  }
}

export class LoginResponse {
  user: string;

  constructor(username: string) {
    this.user = username;
  }
}
