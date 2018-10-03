#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include <iostream>
#include <string>
using namespace std;
using namespace testing;
class IMy{
public:
    ~IMy(){}
    virtual string getString()=0;
};
class MyImpl:IMy{
public:
    MOCK_METHOD0(getString, string());
};
TEST(my, case1){
    EXPECT_THAT("123456789", MatchesRegex("[1-9]*"));
    EXPECT_THAT("1234  56789", ContainsRegex("[1-9]*"));

    //EXPECT_THAT("1234 789", MatchesRegex("[1-9]*"));
    MyImpl mock;
    const char msg[]="hi";
    EXPECT_CALL(mock,getString()).WillRepeatedly(Return(msg));
    EXPECT_EQ(msg,mock.getString());
    const char hello[]="hello";
    EXPECT_CALL(mock,getString()).Times(1).WillOnce(Return(hello));
    string ret = mock.getString();
    cout<<ret<<endl;

    EXPECT_CALL(mock,getString())
        .Times(AtLeast(4))
        .WillOnce(Return("first"))
        .WillOnce(Return("second"))
        .WillRepeatedly(Return("loop"));
    for(size_t i=0;i<5;++i){
        cout<<mock.getString()<<endl;
    }
}
struct S{int m_i;};
class If2{
public:
    virtual int add(int a,int b)=0;
    virtual int minus(int a)=0;
    virtual void get(const S& s)=0;
    virtual void set(const char* s)=0;
};
class Impl2:public If2{
public:
    MOCK_METHOD2(add, int(int,int));
    MOCK_METHOD1(minus, int(int));
    MOCK_METHOD1(get, void(const S&));
    MOCK_METHOD1(set, void(const char*));
};
TEST(t2,case1){
    Impl2 mock;
    EXPECT_CALL(mock,add(Eq(1),Ge(1)));
    mock.add(1,1);
    //mock.add(1,0);// Expected arg #1: is >= 1
    S obj;
    obj.m_i=1;
    EXPECT_CALL(mock,get(Field(&S::m_i, Gt(0))));//Expected arg #0: is an object whose given field is > 0
    mock.get(obj);
}
TEST(t2,case2){
    Impl2 mock;
    EXPECT_CALL(mock,minus(AllOf(Gt(5),Ne(10))));
    mock.minus(6);
    EXPECT_CALL(mock,set(Not(HasSubstr("bb"))));
    mock.set("aa");
    //mock.set("bb");
}
//ResultOf Pointee
