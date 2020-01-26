#!/bin/bash
cd /openmpi-3.1.1 && ./configure && make -j8 && make install && ldconfig
