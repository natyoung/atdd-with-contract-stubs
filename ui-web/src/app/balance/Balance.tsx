"use client"

import {useEffect, useState} from "react";
import BalanceService from "@/app/lib/BalanceService";

const host: string = process.env.API_BASE_URI || 'http://0.0.0.0:8080';

export default function Balance() {
  const [result, setResult] = useState<string>('')

  useEffect(() => {
    const fetchBalance = async () => {
      const response = await new BalanceService(host).balance("1");
      setResult(String(response.data.balance));
    };
    fetchBalance();
  }, []);

  return (
    <div className="mb-4" data-testid="balance">
      {(result) && (
        <span className="balance">{result}</span>
      )}
    </div>
  )
}
