#!/bin/bash
mvn package
mvn exec:java -Dexec.mainClass="com.action.App"