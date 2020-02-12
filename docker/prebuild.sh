#!/bin/bash
apt-get install -y gradle libxml2-dev snapd snapd-xdg-open libgl1-mesa-dev freeglut3-dev libglew-dev libsdl2-dev libsdl2-image-dev libglm-dev libfreetype6-dev
apt-get remove -y libprotobuf-dev libprotobuf*

cp /mynet/docker/environment /etc/
source /etc/environment
cd /libevent && mkdir -p build && cd build && cmake .. && make -j8 && make install && /mynet/docker/makegrpc.sh && cd /mynet/testbed/cpp3p && ./install_openmpi.sh && ./install_thrift.sh && cd /mynet/testbed/cpp3p && ./build.sh

echo "Begin to install kernel dev files"
apt-get remove -y --purge linux-headers-*
apt-get autoremove -y && apt autoclean -y
apt-get -y install --fix-missing kernel-package libelf-dev linux-headers-generic
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF
