#!/bin/bash
# Install gRPC and its dependencies
cd /grpc/third_party/protobuf && ./autogen.sh && ./configure --with-python=no --with-ruby=no --with-php=no --with-objective_c=no && make -j8 && make install
cd /grpc
cmake \
  -DBUILD_SHARED_LIBS=ON \
  -DCMAKE_BUILD_TYPE=Release \
  -DgRPC_BUILD_TESTS=OFF \
  -DgRPC_CARES_PROVIDER=package    \
  -DgRPC_INSTALL=ON \
  -DgRPC_PROTOBUF_PROVIDER=package \
  -DgRPC_SSL_PROVIDER=package      \
  -DgRPC_SSL_PROVIDER=package \
  -DgRPC_ZLIB_PROVIDER=package \
  .
make -j8 install
