import path from "path";
import {MatchersV3, PactV3} from '@pact-foundation/pact';
import DepositService from "../../app/lib/DepositService";
import LoginService from "../../app/lib/LoginService";

const provider = new PactV3({
  dir: path.resolve(process.env.PACT_FOLDER || './pacts'),
  consumer: 'ui-web',
  provider: 'bff-web',
});

describe('POST /deposit', () => {
  it('returns status 200', async () => {
    const amount = 1;
    const accountId = '1'
    const expected = {result: 'success'};
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('accountId 1 exists')
      .uponReceiving('a deposit request')
      .withRequest({
        method: 'POST',
        path: `/casa/deposit/${accountId}`,
        body: {amount: amount}
      })
      .willRespondWith({
        status: 200,
        headers: {
          'Content-Type': 'application/json',
        },
        body: EXPECTED_BODY,
      });

    return provider.executeTest(async (mockserver) => {
      const service = new DepositService(mockserver.url);
      const response = await service.deposit(accountId, amount)

      expect(response.data).toMatchObject(expected);
      expect(response.status).toEqual(200);
    });
  });

  it('returns status 404', async () => {
    const amount = 1;
    const accountId = '2'

    provider
      .given('accountId 2 does not exist')
      .uponReceiving('a deposit request')
      .withRequest({
        method: 'POST',
        path: `/casa/deposit/${accountId}`,
        body: {amount: amount}
      })
      .willRespondWith({
        status: 404
      });

    return provider.executeTest(async (mockserver) => {
      const service = new DepositService(mockserver.url);
      await expect(service.deposit(accountId, amount))
        .rejects.toThrow('Request failed with status code 404');
    });
  });
});

describe('POST /login', () => {
  it('returns status 200', async () => {
    const accountId = '1'
    const password = 'password'
    const expected = {username: '1'};
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('accountId 1 exists')
      .uponReceiving('a login request')
      .withRequest({
        method: 'POST',
        path: `/login`,
        body: {accountId: accountId, password: password}
      })
      .willRespondWith({
        status: 200,
        headers: {
          'Content-Type': 'application/json',
        },
        body: EXPECTED_BODY,
      });

    return provider.executeTest(async (mockserver) => {
      const service = new LoginService(mockserver.url);
      const response = await service.login(accountId, password)

      expect(response.data).toMatchObject(expected);
      expect(response.status).toEqual(200);
    });
  });
});
