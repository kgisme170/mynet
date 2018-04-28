#!/bin/bash
mkdir -p build
thrift -r --gen cpp -o build worker.thrift
thrift -r --gen cpp -o build student.thrift

GEN="build/gen-cpp"
INCLUDE="-I$ODPS_SRC/thirdparty/compatible/include/thrift -I$GEN"
LINKPATH="-L$ODPS_SRC/thirdpaty/compatible/lib"
LIB="-lthrift"

g++ workerclient.cpp $GEN/MyManager.cpp -o build/MyManager_client $INCLUDE $LINKPATH $LIB
g++ $GEN/MyManager_server.skeleton.cpp $GEN/MyManager.cpp -o build/MyManager_server $INCLUDE $LINKPATH $LIB

g++ studentclient.cpp $GEN/Serv.cpp $GEN/student_types.cpp -o build/Student_client $INCLUDE $LINKPATH $LIB
g++ studentserver.cpp $GEN/Serv.cpp $GEN/student_types.cpp -o build/Student_server $INCLUDE $LINKPATH $LIB
