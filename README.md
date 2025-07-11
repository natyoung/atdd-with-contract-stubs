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

The directory to read and write Pact files (reset per acceptance test).

## Steps

Starting with an acceptance test from the user's perspective.

### 1. Create a failing acceptance test

1. Write a specification in `./acceptance-tests/features` based on the user story and ACs.
2. Write the high level steps in `./acceptance-tests/steps` using a ubiquitous domain language to build up a re-usable DSL.
3. Re-use or extend the drivers in `./acceptance-tests/drivers` to isolate the technical details. Use a web driver in `./acceptance-tests/drivers/web` for the first step.
4. Start the UI.
5. Run the test and ensure that it fails.

### 2. UI

#### test case -> ui-web -> bff-web (contract stub) -> verifiable output

At this point, there is no element for the test to interact with, so the test will fail.

1. Test-drive the UI using `./ui-web/src/__tests__`.
2. Create the contract test between `ui-web` (consumer) and `bff-web` (provider).
3. Start the `bff-web` contract stub, e.g. `PACT_FOLDER=<absolute-path-to>/ui-web/pacts PORT=8080 ./go.sh run_pact_stubs`.
4. Run the acceptance test again to smoke test the UI.

### 3. BFF

#### test case -> bff-web -> casa (contract stub) -> verifiable output

1. Run the provider contract verification and see that it fails.
2. Use `./acceptance-tests/drivers/api` to implement the technical details in the acceptance test this time.
3. Satisfy the `ui-web` API contract expectations using TDD. (e.g. this could begin by adding a controller test in CasaResourceTest.kt).
4. Continue with component and unit tests until all the provider verification tests pass.
5. Create the contract test between `bff-web` (consumer) and `casa` (provider).
6. Start the CASA API contract stub, e.g. `PACT_FOLDER=<absolute-path-to>/bff-web/build/pacts PORT=8081 ./go.sh run_pact_stubs`.
7. Run the acceptance test for the BFF.

### 4. CASA

#### test case -> casa -> (3rd party API stubs) -> verifiable output

1. Run the provider contract verification and see that it fails.
2. Create a new endpoint to support the `bff-web` consumer using TDD.
3. See the provider contract verification pass.
4. Stub the third-party API using mockserver by adding an entry in `./mockserver/mockserverInitialization.json`
5. Start mockserver.
6. Start the `casa` API.
7. Run the acceptance test for the `casa` API.
