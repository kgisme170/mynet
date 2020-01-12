#!/bin/bash
cd /
git clone https://github.com/grpc/grpc.git
# grpc
cd grpc/
cd third_party/protobuf/
git submodule update --init --recursive
./autogen.sh
./configure
make -j8
make check -j8
make install
ldconfig
which protoc
protoc --version

cd ../../
make -j8
make install
ldconfig

cd examples/cpp/helloworld/
make -j8
