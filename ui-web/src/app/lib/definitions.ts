export class DepositResponse {
  result: string;

  constructor(result: string) {
    this.result = result;
  }
}

export class LoginResponse {
  user: string;

  constructor(username: string) {
    this.user = username;
  }
}
