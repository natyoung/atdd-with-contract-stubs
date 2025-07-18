import axios, {AxiosResponse} from "axios";
import {BalanceResponse} from "@/app/lib/definitions";

export default class BalanceService {
  private host: string;

  constructor(host: string) {
    this.host = host;
  }

  async balance(accountId: string): Promise<AxiosResponse> {
    return axios.get<BalanceResponse>(`${this.host}/casa/balance/${accountId}`)
      .then(res => res)
  }
}
