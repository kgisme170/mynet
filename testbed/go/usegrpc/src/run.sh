#!/bin/bash
export GOPATH=/mynet/testbed/go/usrgrpc
protoc -I hello/ hello/helloworld.proto --go_out=plugins=grpc:hello
