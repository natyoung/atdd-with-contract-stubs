import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import Page from '@/app/deposit/page';

jest.mock('next-auth', () => ({
  getServerSession: jest.fn().mockResolvedValue({user: {id: '1'}}), // Mock a valid session
}));

jest.mock('../../app/api/auth/[...nextauth]/route', () => ({
  authOptions: {}, // Mock authOptions to prevent loading the real route with CredentialsProvider
}));

describe('deposit page', () => {
  it('renders the deposit form', async () => {
    const pageContent = await Page(); // Await the async Page component
    render(pageContent); // Render the resolved JSX
    const amountInput = screen.getByTestId('amount');
    expect(amountInput).toBeInTheDocument();
    const submitButton = screen.getByTestId('submit-amount');
    expect(submitButton).toBeInTheDocument();
  });
});
