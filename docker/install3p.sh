#!/bin/bash
apt-get install -y gradle uuid-dev libxml2-dev snapd snapd-xdg-open libgl1-mesa-dev freeglut3-dev libglew-dev libsdl2-dev libsdl2-image-dev libglm-dev libfreetype6-dev && apt-get remove -y libprotobuf-dev libprotobuf* && cp ~/mynet/docker/environment /etc/ && source /etc/environment
[ $? -ne 0 ] && echo "Install extra dev tools failed" && exit 1

cd / && rm -fr /libevent && git clone https://github.com/libevent/libevent.git && cd / && rm -fr /grpc && git clone https://github.com/grpc/grpc.git
cd /grpc/third_party/protobuf/ && git submodule update --init --recursive && cd / && wget https://download.open-mpi.org/release/open-mpi/v3.1/openmpi-3.1.1.tar.gz && tar xvf openmpi-3.1.1.tar.gz && cd / && wget http://archive.apache.org/dist/thrift/0.10.0/thrift-0.10.0.tar.gz && tar xvf thrift-0.10.0.tar.gz
[ $? -ne 0 ] && echo "Download 3p source code failed" && exit 1

apt-get remove -y --purge linux-headers-* && apt-get autoremove -y && apt autoclean -y && apt-get -y install --fix-missing kernel-package libelf-dev linux-headers-generic && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF && go get -u github.com/golang/protobuf/{proto,protoc-gen-go}

[ $? -ne 0 ] && echo "linux kernel dev install failed" && exit 1
echo "install3pSource.sh successful"
