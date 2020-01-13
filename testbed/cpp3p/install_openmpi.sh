#!/bin/bash
cd / && wget https://download.open-mpi.org/release/open-mpi/v3.1/openmpi-3.1.1.tar.gz && tar xvf openmpi-3.1.1.tar.gz && cd openmpi-3.1.1 && ./configure && make -j8 && make install && ldconfig
