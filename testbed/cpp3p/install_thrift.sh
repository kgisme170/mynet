#!/bin/bash
#thrift
cd /
wget http://archive.apache.org/dist/thrift/0.10.0/thrift-0.10.0.tar.gz
tar xvf thrift-0.10.0.tar.gz
cd thrift-0.10.0
./configure --with-boost=no --with-lua=no --with-ruby=no --with-d=no --with-qt4=no --with-qt-5=no --with-c_glib=no --with-nodejs=no --with-perl=no --with-php=no --with-php_extension=no --with-dart=no --with-haskell=no --with-haxe=no --with-python=no
make -j8
make check -j8
make install
ldconfig
