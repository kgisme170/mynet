#!/bin/bash
apt-get install -y libxml2-dev
apt remove libprotobuf-dev libprotobuf*

cp /mynet/docker/environment /etc/
source /etc/environment
cd /libevent && mkdir -p build && cd build && cmake .. && make -j8 && make install && /mynet/docker/makegrpc.sh && cd /mynet/testbed/cpp3p && ./install_openmpi.sh && ./install_thrift.sh && cd /mynet/testbed/cpp3p && ./build.sh
