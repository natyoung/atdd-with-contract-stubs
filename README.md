# Testing techniques for CI and CD

## Project structure

    go.sh                        # Scripts to set up the environment and run the tests.
    ├── acceptance-tests         #
    │   ├── test_cases           # The what. Test cases derived from the AC's.
    │   ├── dsl                  # Using the ubiquitous domain language.
    │   ├── drivers              # The how. Instrumentation per device.
    ├── mockserver               # 3rd party bank's API stub.
    ├── casa                     # CASA account bounded context.
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
