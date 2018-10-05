#!/bin/bash
[ $# -ne 1 ] && echo "$0 [cppVersion]" && exit 1
CPPVER=$1
TESTFILE=/tmp/testcpp.cpp
cat << EOF > $TESTFILE
#include<unordered_map>
int main(){
    std::unordered_map<int, int> mp;
    return 0;
}
EOF
g++ $TESTFILE -std=c++${CPPVER} -c -o /tmp/test${CPPVER}.o > /dev/null 2>&1