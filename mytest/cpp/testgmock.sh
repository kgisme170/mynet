#!/bin/bash
TESTFILE=/tmp/testgmock.cpp
cat << EOF > $TESTFILE
#include <gtest/gtest.h>
#include <gmock/gmock.h>
int main(){
    return 0;
}
EOF
g++ $TESTFILE -c -o /tmp/testgmock.o > /dev/null 2>&1