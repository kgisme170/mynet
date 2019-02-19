#!/bin/bash
mkdir -p build
cd build
cmake ../
make -j8 VERBOSE=1
