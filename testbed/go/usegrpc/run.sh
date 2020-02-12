#!/bin/bash

protoc --go_out=plugin=grpc:. helloworld.proto
