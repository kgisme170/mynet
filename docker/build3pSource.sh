#!/bin/bash
cd /libevent && mkdir -p build && cd build && cmake .. && make -j8 && make install && cd /grpc && bazel build :all && cd /mynet/testbed/cpp3p && ./install_openmpi.sh && ./install_thrift.sh

cd /mynet/testbed/cpp3p && ./build.sh

[ $? -ne 0 ] && echo "prebuild.sh failed" && exit 1
echo "build3pSource.sh successful"
