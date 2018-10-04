#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include <iostream>
#include <string>
using namespace std;
using namespace testing;
class If2{
public:
    virtual char* getS()=0;
    virtual int add()=0;
};
class Impl2:public If2{
public:
    MOCK_METHOD0(getS, char*());
    MOCK_METHOD0(add, int());
};
//ResultOf Pointee
TEST(t2,case3){
    Impl2 mock;
    char msg[]="msg";

    EXPECT_CALL(mock,getS).WillOnce(Return((char*)NULL));
    EXPECT_CALL(mock,add).WillOnce(Return(1));
}