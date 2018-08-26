#!/bin/bash
yum install -y gcc-c++ autoconf libtool
yum groupinstall -y "Development Tools"
git clone https://github.com/grpc/grpc.git
cd grpc
git submodule update --init
cd third_party/protobuf/
./autogen
./configure
make
sudo make install
sudo ldconfig
cd ../..
make
sudo make install
sudo ldconfig
