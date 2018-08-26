#!/bin/bash
protoc --cpp_out=./ hello.proto
protoc --cpp_out=./ embed.proto
protoc --cpp_out=./ serialize.proto
protoc --python_out=./ pytest.proto