# ui-web

## Installing

```bash
yarn
```

## Run

```bash
export NEXTAUTH_URL=http://localhost:3000 # URI of this app
export NEXTAUTH_SECRET=some_long_random_secret # Generate with `openssl rand -base64 32` or similar
export API_BASE_URI=http://localhost:8080 # URI of bff-web

yarn dev
```

## Test

```text
PACT_FOLDER=./pacts yarn test
```
