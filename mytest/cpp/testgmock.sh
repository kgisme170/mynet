#!/bin/bash
TESTFILE=/tmp/testgmock.cpp
cat << EOF > $TESTFILE
#include <gtest/gtest.h>
#include <gmock/gmock.h>
int main(int argc, char* argv[]){
    testing::InitGoogleTest(&argc,argv);
    return RUN_ALL_TESTS();
}
EOF
g++ $TESTFILE -c -o /tmp/testgmock.o -lgmock -lgtest -lpthread> /dev/null 2>&1