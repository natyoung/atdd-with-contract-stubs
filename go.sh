#!/usr/bin/env bash

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

print_usage()
{
cat <<EOF

    setup               Initialise the apps.

EOF
}

case $1 in
setup)
setup
;;
*)
print_usage
;;
esac
