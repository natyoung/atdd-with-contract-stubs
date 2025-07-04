# test-automation


## Project structure

    go.sh                        # Scripts to set up the environment and run the tests.
    ├── acceptance-tests         # An acceptance testing framework.
    ├── mockserver               # Used for stubbing the 3rd party API.
    ├── casa                     # A service containing the CASA account bounded context.
    ├── bff-web                  # A backend-for-frontend serving the web UI.
    ├── ui-web                   # Web UI.
    └── ...

## Dependencies

- python3
- pip3
- yarn
- wget
- java
- docker
- docker-compose

## Env vars

### PACT_FOLDER
The directory to read and write Pact files.

# Outside in test-driven development

- Acceptance test-driven development
- Contract test-driven development
- Test-driven development

## Steps

### 1. Create a failing acceptance test

1. Write a test case in `./acceptance-tests/test_cases`.
2. In the test case, use and/or extend the DSL from `./acceptance-tests/dsl`.
3. In the DSL, use and/or extend a driver from `./acceptance-tests/drivers`.
4. Start the UI.
5. Run the test and ensure that it fails.

### 2. UI

#### test case -> ui-web -> verifiable output

At this point, there is no element for the test to interact with, so the test will fail.

1. Test-drive the UI using `./ui-web/src/__tests__`.
2. Create the contract test between the UI (consumer) and the BFF (provider).
3. Start the BFF contract stub `PACT_FOLDER=<dir> ./go.sh run_pact_stubs`.
4. Run the acceptance test again to smoke test the UI.

### 3. BFF

#### test case -> ui-bff -> verifiable output

1. Run the provider contract verification and see that it fails.
2. Create a new endpoint to support the UI consumer using TDD.
3. See the provider contract verification pass.
4. Create the contract test between the BFF (consumer) and the CASA API (provider).
5. Create an API driver to test the BFF.
6. Start the BFF.
7. Start the CASA API contract stub `PACT_FOLDER=<dir> ./go.sh run_pact_stubs`.
8. Run the acceptance test for the BFF.

### 4. CASA

#### test case -> casa -> verifiable output

1. Run the provider contract verification and see that it fails.
2. Create a new endpoint to support the BFF consumer using TDD.
3. See the provider contract verification pass.
4. Stub the third-party API using mockserver by adding an entry in `./mockserver/mockserverInitialization.json`
5. Start mockserver.
6. Start the CASA API.
7. Use the API driver to test the CASA API.
8. Run the acceptance test for the CASA API.
