#!/bin/bash
cd /grpc/third_party/protobuf && ./autogen.sh && ./configure && make -j8 && make install -j8 && cd /grpc/ && bazel build :all && make install -j8
