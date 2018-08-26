#!/bin/bash
mkdir -p build
thrift -r --gen cpp -o build worker.thrift
thrift -r --gen cpp -o build student.thrift

GEN="build/gen-cpp"
#INCLUDE="-I/usr/local/include/thrift -I$GEN"
#LIB="-L /usr/local/lib -lthrift -lthriftnb -levent"
#CXX="g++"
#$CXX workerclient.cpp $GEN/MyManager.cpp -o build/MyManager_client $INCLUDE $LINKPATH $LIB
#$CXX $GEN/MyManager_server.skeleton.cpp $GEN/MyManager.cpp -o build/MyManager_server $INCLUDE $LIB

#$CXX studentclient.cpp $GEN/Serv.cpp $GEN/student_types.cpp -o build/Student_client $INCLUDE $LIB
#$CXX studentserver.cpp $GEN/Serv.cpp $GEN/student_types.cpp -o build/Student_server $INCLUDE $LIB
