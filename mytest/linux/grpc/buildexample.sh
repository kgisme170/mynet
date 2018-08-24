#!/bin/bash
protoc --cpp_out=./ examples.proto
protoc --cpp_out=./ hello.proto
protoc --cpp_out=./ embed.proto
protoc --cpp_out=./ serialize.proto
protoc --python_out=./ pytest.proto
# protoc --grpc_out=./ --plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin examples.proto
