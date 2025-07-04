# Acceptance tests

This project uses a layered design to make the test cases less brittle when lower level implementation details change.

- What
    - [test_cases](./test_cases) - Derived from the acceptance criteria.
    - [dsl](./dsl) - Use a ubiquitous domain language.
- How
    - [drivers](./drivers) - Instrumentation per device.

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

Run

```bash
pytest
```
