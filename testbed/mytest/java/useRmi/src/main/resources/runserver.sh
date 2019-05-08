#!/bin/sh
java -cp ../useRmi-1.0-SNAPSHOT.jar -Djava.rmi.server.codebase=http://compute.jar -Djava.rmi.server.hostname=mycomputer.example.com -Djava.security.policy=server.policy engine.ComputeEngine