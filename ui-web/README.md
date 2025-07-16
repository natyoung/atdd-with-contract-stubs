# ui-web

## Installing

```bash
yarn
```

## Run

```bash
# Generate with `openssl rand -base64 32` or similar

export NEXTAUTH_URL=http://localhost:3000
export NEXTAUTH_SECRET=some_long_random_secret
export API_BASE_URI=http://localhost:8080
  
yarn dev
```

## Test

```text
PACT_FOLDER=./pacts yarn test
```
