#!/usr/bin/env bash
if [[ -z "${PACT_FOLDER}" ]]; then
  echo "PACT_FOLDER not set"
  exit 1;
else
  echo "PACT_FOLDER is se to: ${PACT_FOLDER}"
fi

dependency_check() {
  command -v python3 >/dev/null 2>&1 || { echo >&2 "python3 was not found.  Aborting."; exit 1; }
  command -v pip3 >/dev/null 2>&1 || { echo >&2 "pip3 was not found.  Aborting."; exit 1; }
  command -v yarn >/dev/null 2>&1 || { echo >&2 "yarn was not found.  Aborting."; exit 1; }
  command -v wget >/dev/null 2>&1 || { echo >&2 "wget was not found.  Aborting."; exit 1; }
  command -v java >/dev/null 2>&1 || { echo >&2 "java was not found.  Aborting."; exit 1; }
  command -v docker >/dev/null 2>&1 || { echo >&2 "docker is not running.  Aborting."; exit 1; }
  command -v docker-compose >/dev/null 2>&1 || { echo >&2 "docker-compose was not found.  Aborting."; exit 1; }
}

setup()
{
  dependency_check
  docker pull pactfoundation/pact-stub-server:latest
  docker pull mockserver/mockserver:latest
  pushd ./acceptance-tests
  python3 -m pip install --user virtualenv
  python3 -m venv env
  pip3 install -r requirements.txt
  playwright install
  popd
  pushd ./ui-web
  yarn install
  popd
  pushd ./bff-web
  ./gradlew clean
  popd
  pushd ./casa
  ./gradlew clean
  popd
}

run_pact_stubs()
{
  docker run --rm -t --name pact-stubs -p ${PORT}:${PORT} -v "${PACT_FOLDER}:/app/pacts" pactfoundation/pact-stub-server -p ${PORT} -d pacts --cors
}

print_usage()
{
cat <<EOF

    setup               Initialise the apps.
    run_pact_stubs      Uses the contracts in $PACT_FOLDER as stub services for testing.

EOF
}

case $1 in
setup)
setup
;;
run_pact_stubs)
run_pact_stubs
;;
*)
print_usage
;;
esac
