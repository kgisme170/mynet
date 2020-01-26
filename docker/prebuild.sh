#!/bin/bash
cp /mynet/docker/environment /etc/
source /etc/environment
cd /mynet/testbed/cpp3p && ./install_grpc.sh && ./install_openmpi.sh && ./install_thrift.sh && cd /mynet/testbed/cpp3p && ./build.sh
