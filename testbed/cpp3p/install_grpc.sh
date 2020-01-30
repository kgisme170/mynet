#!/bin/bash
cd /grpc/third_party/protobuf && bazel build :all && make install -j8 && cd /grpc/ && bazel build :all && make install -j8
