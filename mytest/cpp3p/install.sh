#!/bin/bash
git clone https://github.com/grpc/grpc.git
# grpc
tar xvf 'grpc.tar.gz'
cd grpc/
cd third_party/protobuf/
git submodule update --init --recursive
./autogen.sh
./configure
make -j8
make check -j8
sudo make install
sudo ldconfig
which protoc
protoc --version

cd ../../
make -j8
sudo make install
sudo ldconfig

cd examples/cpp/helloworld/
make -j8
./greeter_server
./greeter_client

#thrift
sudo apt-get install ant libboost-dev libboost-test-dev libboost-program-options-dev libboost-filesystem-dev libboost-thread-dev libevent-dev automake libtool flex bison pkg-config g++ libssl-dev
wget http://archive.apache.org/dist/thrift/0.10.0/thrift-0.10.0.tar.gz
tar xvf thrift-0.10.0.tar.gz
cd thrift-0.10.0
./configure --with-boost=no --with-lua=no --with-ruby=no --with-d=no --with-qt4=no --with-qt-5=no --with-c_glib=no --with-nodejs=no --with-perl=no --with-php=no --with-php_extension=no --with-dart=no --with-haskell=no --with-haxe=no --with-python=no
make -j8
make check -j8
sudo make install
sudo ldconfig

#openmpi
wget https://download.open-mpi.org/release/open-mpi/v3.1/openmpi-3.1.1.tar.gz
make -j8
sudo make install
sudo ldconfig

#misc
apt-get install libleveldb-dev
apt-get install liblz4-dev
apt-get install libsnappy-dev
apt-get install libzstd-dev
apt-get install uuid-dev
