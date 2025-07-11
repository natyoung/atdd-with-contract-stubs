# Acceptance tests

This project uses a layered design to make the test cases less brittle when lower level implementation details change.

- What
  - [features](./features) - Features and scenarios ere derived from the user story and acceptance criteria.
  - [steps](./steps) - Uses a ubiquitous domain language to abstract implementation details.
- How
  - [drivers](./drivers) - Technical details protecting the higher level layers from changes to implementation details.

### Dependency map:

User story -> AC -> Test Cases -> DSL -> Drivers -> SUT

## Installing

```bash
python3 -m pip install --user virtualenv
python3 -m venv env
source env/bin/activate
pip install -r requirements.txt
playwright install
```

## Run

Tests are run per service using its driver, e.g.

- ```pytest --driver=web```
- ```pytest --driver=bff_api```
- ```pytest --driver=casa_api```

Add more drivers as needed.


### Dependencies

Depending on the os, the following may be needed for psycopg2:

```text
brew install openssl@3 libpq postgresql

export LDFLAGS="-L$(brew --prefix openssl@3)/lib -L$(brew --prefix libpq)/lib"
export CPPFLAGS="-I$(brew --prefix openssl@3)/include -I$(brew --prefix libpq)/include"
export PATH="$(brew --prefix libpq)/bin:$PATH"

xcode-select --install

pip install psycopg2
```
