import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import Page from '@/app/login/page';

jest.mock('next-auth', () => ({
  getServerSession: jest.fn().mockResolvedValue(null), // Mock no session to render the form
}));

jest.mock('../../app/api/auth/[...nextauth]/route', () => ({
  authOptions: {}, // Mock authOptions to prevent loading the real route
}));

describe('login page', () => {
  it('renders the login form', async () => {
    const pageContent = await Page(); // Await the async Page component
    render(pageContent); // Render the resolved JSX
    const accountId = screen.getByTestId('accountId');
    expect(accountId).toBeInTheDocument();
    const password = screen.getByTestId('password');
    expect(password).toBeInTheDocument();
    const submitButton = screen.getByTestId('submit-login');
    expect(submitButton).toBeInTheDocument();
  });
});