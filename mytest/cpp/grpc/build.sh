#!/bin/bash
protoc --cpp_out=./ examples.proto
protoc --grpc_out=./ --plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin examples.proto
