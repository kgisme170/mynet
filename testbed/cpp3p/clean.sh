#!/bin/bash
cd /mynet/testbed/cpp3p/misc && make -j8 clean && cd /mynet/testbed/cpp3p/protobuf && cmake . && make -j8 clean && cd /mynet/testbed/cpp3p/usegrpc && make -j8 clean && cd /mynet/testbed/cpp3p/usethrift && make -j8 clean && cd /mynet/testbed/cpp3p/useXml2 && make -j8 clean
