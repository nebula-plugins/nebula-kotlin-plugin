#!/bin/bash
# This script will build the project.

SWITCHES="--info --stacktrace"
RELEASE_BUILD_ONLY="Skipping build. This project only supports release builds due to dependency on project version for Kotlin plugin dependencies"

if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
  echo -e "Build Pull Request #$TRAVIS_PULL_REQUEST => Branch [$TRAVIS_BRANCH]"
  echo $RELEASE_BUILD_ONLY
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_TAG" == "" ]; then
  echo -e 'Build Branch with Snapshot => Branch ['$TRAVIS_BRANCH']'
  echo $RELEASE_BUILD_ONLY
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_TAG" != "" ]; then
  echo -e 'Build Branch for Release => Branch ['$TRAVIS_BRANCH']  Tag ['$TRAVIS_TAG']'
  case "$TRAVIS_TAG" in
  *-rc\.*)
    echo $RELEASE_BUILD_ONLY
    ;;
  *)
    ./gradlew -Prelease.travisci=true -Prelease.useLastTag=true final $SWITCHES
    ;;
  esac
else
  echo -e 'WARN: Should not be here => Branch ['$TRAVIS_BRANCH']  Tag ['$TRAVIS_TAG']  Pull Request ['$TRAVIS_PULL_REQUEST']'
  echo $RELEASE_BUILD_ONLY
fi
