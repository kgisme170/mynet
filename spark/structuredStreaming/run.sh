#!/bin/bash
mvn exec:java -Dexec.mainClass="wordCountWindowed" -Dexec.args="localhost 9999 10 10"
