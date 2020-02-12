#!/bin/bash
protoc -I hello/ hello/helloworld.proto --go_out=plugins=grpc:hello
cd hello && go build helloworld.pb.go
