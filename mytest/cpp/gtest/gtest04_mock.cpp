#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include <iostream>
#include <string>
using namespace std;
class IMy{
public:
    ~IMy(){}
    virtual string getString()=0;
};
class MyImpl:IMy{
    MOCK_METHOD0(getString, string());
};