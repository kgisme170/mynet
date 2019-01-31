#!/bin/bash
sudo yum remove thrift
sudo yum remove thrift-devel
wget https://mirrors.cnnic.cn/apache/thrift/0.10.0/thrift-0.10.0.tar.gz
tar xvf thrift-0.10.0.tar.gz 
cd thrift-0.10.0/
./configure --with-lua=no --with-ruby=no
make -j4
sudo make install
