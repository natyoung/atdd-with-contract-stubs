import '@testing-library/jest-dom'
import {render, screen, waitFor} from '@testing-library/react'
import {faker} from '@faker-js/faker';
import Balance from "@/app/balance/Balance";
import BalanceService from "@/app/lib/BalanceService";
import { AxiosResponse } from 'axios';

afterEach(() => {
  jest.clearAllMocks();
});

describe('<Balance />', () => {
  it('shows the result', async () => {
    const accountId = '1'
    const balance = faker.number.int()
    const serviceMock = jest.spyOn(BalanceService.prototype, 'balance')
      .mockImplementation((accountId: string) => Promise.resolve({
        data: { balance: balance },
        status: 200,
        statusText: 'OK',
        headers: {},
        config: { headers: {} },
        request: {}
      } as AxiosResponse<{ balance: number }>));

    render(<Balance/>);

    await waitFor(() => {
      expect(screen.getByTestId('balance')).toHaveTextContent(String(balance));
    });
    expect(serviceMock).toHaveBeenCalledTimes(1);
    expect(serviceMock).toHaveBeenCalledWith(accountId);
  });
});
