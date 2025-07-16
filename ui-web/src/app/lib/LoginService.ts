import axios, {AxiosResponse} from "axios";
import {LoginResponse} from "@/app/lib/definitions";

export default class LoginService {
  private host: string;

  constructor(host: string) {
    this.host = host;
  }

  async login(accountId: string, password: string): Promise<AxiosResponse> {
    return axios.post<LoginResponse>(`${this.host}/login`, {
      accountId: accountId,
      password: password,
    })
      .then(res => res)
  }
}
