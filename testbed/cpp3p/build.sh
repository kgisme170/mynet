#!/bin/bash
/mynet/testbed/cpp3p/install_grpc.sh && /mynet/testbed/cpp3p/install_openmpi.sh && /mynet/testbed/cpp3p/install_thrift.sh
cd /mynet/testbed/cpp3p/misc      && cmake . && make -j8
cd /mynet/testbed/cpp3p/protobuf  && cmake . && make -j8
cd /mynet/testbed/cpp3p/usegrpc   && cmake . && make -j8
#cd /mynet/testbed/cpp3p/usethrift && cmake . && make -j8
cd /mynet/testbed/cpp3p/useXml2   && cmake . && make -j8
cd /mynet/testbed/cpp3p/opengl    && ./buildopengl.sh
