import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import Page from '@/app/balance/page';

jest.mock('next-auth', () => ({
  getServerSession: jest.fn().mockResolvedValue({user: {id: '1'}}),
}));

jest.mock('../../app/api/auth/[...nextauth]/route', () => ({
  authOptions: {},
}));

describe('balance page', () => {
  it('renders the balance', async () => {
    const pageContent = await Page(); // Await the async Page component
    render(pageContent); // Render the resolved JSX
    const amountInput = screen.getByTestId('balance');
    expect(amountInput).toBeInTheDocument();
  });
});
