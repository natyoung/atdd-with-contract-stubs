"use client"

import { FormEvent, useState } from "react";
import { signIn } from "next-auth/react";

export default function LoginForm() {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [success, setSuccess] = useState<string>("");

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsLoading(true);
    setError("");
    setSuccess("");

    const formData = new FormData(event.currentTarget);
    const accountId = formData.get("accountId") as string;
    const password = formData.get("password") as string;

    try {
      const result = await signIn("credentials", {
        accountId: accountId,
        password,
        redirect: true,
      });

      if (result?.error) {
        setError(result.error);
      } else {
        setSuccess("Login successful!");
      }
    } catch (err) {
      setError(`Error: ${err}`);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={onSubmit} className="bg-dark shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div className="mb-4">
        <label className="block text-blue-500 text-sm font-bold mb-2" htmlFor="accountId">
          Account ID
        </label>
        <input
          className="shadow appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="text" name="accountId" data-testid="accountId" required
        />
      </div>
      <div className="mb-4">
        <label className="block text-blue-500 text-sm font-bold mb-2" htmlFor="password">
          Password
        </label>
        <input
          className="shadow appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="password" name="password" data-testid="password" required
        />
      </div>
      <div className="items-center justify-between mb-4">
        <button
          className="bg-blue-800 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit" disabled={isLoading} data-testid="submit-login">
          {isLoading ? "Loading..." : "Login"}
        </button>
      </div>
      {error && (
        <div className="error text-red-500" data-testid="error">{error}</div>
      )}
      {success && (
        <div className="success text-green-500" data-testid="success">{success}</div>
      )}
    </form>
  );
}