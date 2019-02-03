#bin/sh
java -cp ../useRmi-1.0-SNAPSHOT.jar -Djava.rmi.server.codebase=http://mysecondcomputer/~jones/classes/ -Djava.security.policy=client.policy client.ComputePi mycomputer.example.com 45
