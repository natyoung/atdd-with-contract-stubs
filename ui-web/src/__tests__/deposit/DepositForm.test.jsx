import '@testing-library/jest-dom'
import {fireEvent, render, screen, waitFor} from '@testing-library/react'
import DepositForm from '@/app/deposit/DepositForm'
import DepositService from '@/app/lib/DepositService'
import {faker} from '@faker-js/faker';

afterEach(() => {
  jest.clearAllMocks();
});

describe('<DepositForm />', () => {
  it('takes a deposit amount', async () => {
    await render(<DepositForm/>);
    const input = screen.getByTestId('amount')
    expect(input).toBeInTheDocument()
  });

  it('sends the amount', async () => {
    const accountId = '1'
    const amount = faker.number.int()
    const serviceMock = jest.spyOn(DepositService.prototype, 'deposit')
      .mockImplementation(() => Promise.resolve({data: {result: faker.animal.cow(), status: faker.number.int()}}));

    await waitFor(() => {
      render(<DepositForm/>);
      fireEvent.change(screen.getByTestId('amount'), {target: {value: amount}})
      fireEvent.click(screen.getByTestId('submit-amount'))
      expect(serviceMock).toHaveBeenCalledTimes(1);
      expect(serviceMock).toHaveBeenCalledWith(accountId, amount);
    })
  });

  it('shows the result', async () => {
    jest.spyOn(DepositService.prototype, 'deposit')
      .mockImplementation(() => Promise.resolve({data: {result: faker.animal.cow()}, status: 200}));

    await waitFor(() => {
      render(<DepositForm/>);
      fireEvent.change(screen.getByTestId('amount'), {target: {value: faker.number.int()}})
      fireEvent.click(screen.getByTestId('submit-amount'))
    })

    const error = screen.getByTestId('result')
    expect(error.textContent.length).toBeGreaterThan(0);
  });
});
