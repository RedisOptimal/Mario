#!/bin/bash
rm -rf ./target
mvn assembly:assembly -DskipTests
mvn install -DskipTests
