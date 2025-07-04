import path from "path";
import {MatchersV3, PactV3} from '@pact-foundation/pact';
import DepositService from "../../app/lib/DepositService";

const provider = new PactV3({
  dir: path.resolve(process.env.PACT_FOLDER),
  consumer: 'ui-web',
  provider: 'bff-web',
});

const accountId = '1'

describe('POST /deposit', () => {
  it('returns status 200', async () => {
    const amount = 1;
    const expected = {result: 'success'};
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('accountId 1 exists')
      .uponReceiving('a deposit request')
      .withRequest({
        method: 'POST',
        path: `/deposit/${accountId}`,
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
      const walletService = new DepositService(mockserver.url);
      const response = await walletService.deposit(accountId, amount)

      expect(response.data).toMatchObject(expected);
      expect(response.status).toEqual(200);
    });
  });
});
