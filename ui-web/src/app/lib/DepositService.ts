import axios, {AxiosResponse} from "axios";
import {DepositResponse} from "@/app/lib/definitions";

export default class DepositService {
  private host: string;

  constructor(host: string) {
    this.host = host;
  }

  async deposit(accountId: string, amount: number): Promise<AxiosResponse> {
    return axios.post<DepositResponse>(`${this.host}/deposit/${accountId}`, {
      amount: amount
    })
      .then(res => res)
  }
}
