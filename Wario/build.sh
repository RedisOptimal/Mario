#!/bin/bash
rm -rf ./target
mvn assembly:assembly
mvn install
