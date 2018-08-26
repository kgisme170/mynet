#!/bin/bash
git clone https://github.com/libevent/libevent.git
cd libevent
sudo apt-get install cmake
sudo apt-get install libssl-dev
mkdir build && cd build
cmake .. # Default to Unix Makefiles.
make -j8
sudo make install
