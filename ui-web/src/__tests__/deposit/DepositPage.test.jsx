import '@testing-library/jest-dom'
import {render, screen} from '@testing-library/react'
import Page from '@/app/deposit/page';

describe('deposit page', () => {
  it('renders the deposit form', () => {
    render(<Page />);
    const amountInput = screen.getByTestId('amount');
    expect(amountInput).toBeInTheDocument();
    const submitButton = screen.getByTestId('submit-amount');
    expect(submitButton).toBeInTheDocument();
  });
})
