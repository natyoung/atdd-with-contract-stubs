"use client"

import {FormEvent, useState} from "react";
import DepositService from "@/app/lib/DepositService";

const host: string = process.env.API_BASE_URI || 'http://0.0.0.0:8080';

export default function DepositForm() {
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [result, setResult] = useState<string>('')

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setIsLoading(true)
    setResult('')
    const service = new DepositService(host)
    const formData = new FormData(event.currentTarget)
    const amount = formData.get('amount')

    try {
      const response = await service.deposit('1', Number(amount))
      setResult(response.data.result)
    } catch (error) {
      setResult(`Error: ${error}`)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <form onSubmit={onSubmit} className="bg-dark shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div className="mb-4">
        <label className="block text-blue-500  text-sm font-bold mb-2" htmlFor="amount">
          Amount
        </label>
        <input
          className="shadow appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="text" name="amount" data-testid="amount"
        />
      </div>
      <div className="items-center justify-between mb-4">
        <button
          className="bg-blue-800 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit" disabled={isLoading} data-testid="submit-amount">
          {isLoading ? 'Loading...' : 'Deposit'}
        </button>
      </div>
      {(result) && (
        <div className="result" data-testid="result">{result}</div>
      )}
    </form>
  )
}
