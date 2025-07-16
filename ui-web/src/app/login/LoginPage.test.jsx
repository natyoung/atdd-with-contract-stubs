import '@testing-library/jest-dom'
import {render, screen} from '@testing-library/react'
import Page from '@/app/login/page';

describe('login page', () => {
  it('renders the login form', () => {
    render(<Page />);
    const accountId = screen.getByTestId('accountId');
    expect(accountId).toBeInTheDocument();
    const password = screen.getByTestId('password');
    expect(password).toBeInTheDocument();
    const submitButton = screen.getByTestId('submit-login');
    expect(submitButton).toBeInTheDocument();
  });
})
