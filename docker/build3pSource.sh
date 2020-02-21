#!/bin/bash
cd /libevent && mkdir -p build && cd build && cmake .. && make -j8 && make install && ~/mynet/docker/makegrpc.sh
cd ~/mynet/testbed/cpp3p && ./install_openmpi.sh && ./install_thrift.sh && ./build.sh

[ $? -ne 0 ] && echo "build3pSource.sh failed" && exit 1
echo "build3pSource.sh successful"
