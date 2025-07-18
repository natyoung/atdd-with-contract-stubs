import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import LoginForm from '@/app/login/LoginForm';
import {signIn} from 'next-auth/react';
import {faker} from '@faker-js/faker';

jest.mock('next-auth/react');

afterEach(() => {
  jest.clearAllMocks();
});

describe('<LoginForm />', () => {
  it('renders accountId and password inputs', () => {
    render(<LoginForm/>);
    const accountId = screen.getByTestId('accountId');
    const password = screen.getByTestId('password');
    expect(accountId).toBeInTheDocument();
    expect(password).toBeInTheDocument();
  });

  it('submits credentials and handles success', async () => {
    const accountId = faker.number.int().toString();
    const password = faker.internet.password();
    const signInMock = jest.mocked(signIn);
    signInMock.mockResolvedValue({error: null, url: '', ok: true, status: 200});

    await waitFor(() => {
      render(<LoginForm/>);
      fireEvent.change(screen.getByTestId('accountId'), {target: {value: accountId}});
      fireEvent.change(screen.getByTestId('password'), {target: {value: password}});
      fireEvent.click(screen.getByTestId('submit-login'));
    });

    expect(signInMock).toHaveBeenCalledTimes(1);
    expect(signInMock).toHaveBeenCalledWith('credentials', {
      accountId: accountId,
      password,
      redirect: true,
    });

    const success = await screen.findByTestId('success');
    expect(success).toBeInTheDocument();
    expect(success.textContent).toBe('Login successful!');
  });

  it('submits credentials and handles error', async () => {
    const accountId = faker.number.int().toString();
    const password = faker.internet.password();
    const errorMessage = faker.lorem.sentence();
    const signInMock = jest.mocked(signIn);
    signInMock.mockResolvedValue({error: errorMessage, url: '', ok: false, status: 401});

    await waitFor(() => {
      render(<LoginForm/>);
      fireEvent.change(screen.getByTestId('accountId'), {target: {value: accountId}});
      fireEvent.change(screen.getByTestId('password'), {target: {value: password}});
      fireEvent.click(screen.getByTestId('submit-login'));
    });

    expect(signInMock).toHaveBeenCalledTimes(1);
    expect(signInMock).toHaveBeenCalledWith('credentials', {
      accountId,
      password,
      redirect: true,
    });

    const error = await screen.findByTestId('error');
    expect(error).toBeInTheDocument();
    expect(error.textContent).toBe(errorMessage);
  });
});
