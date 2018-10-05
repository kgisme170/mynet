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
    virtual int add2(int)=0;
    virtual int& getRef()=0;
    virtual int*& getPointerRef()=0;
    virtual void setParam(int* pi)=0;
};
class Impl2:public If2{
public:
    MOCK_METHOD0(getS, char*());
    MOCK_METHOD0(add, int());
    MOCK_METHOD1(add2, int(int));
    MOCK_METHOD0(getRef, int&());
    MOCK_METHOD0(getPointerRef, int*&());
    MOCK_METHOD1(setParam, void(int*));
};
//ResultOf Pointee
TEST(t2,case3){
    Impl2 mock;
    char msg[]="msg";

    EXPECT_CALL(mock,getS).WillOnce(Return((char*)NULL));
    EXPECT_CALL(mock,add).WillOnce(Return(1));
    mock.getS();
    mock.add();
}
TEST(t2,case4){
    Impl2 mock;
    EXPECT_CALL(mock,getS).WillOnce(ReturnNull());
    mock.getS();

    int m_i=0;
    EXPECT_CALL(mock,getRef).WillOnce(ReturnRef(m_i));
    int& ri=mock.getRef();
    ri=1;
    EXPECT_EQ(1,m_i);

    int* pi = &m_i;
    int* p2 = new int(2);
    EXPECT_CALL(mock,getPointerRef).WillOnce(ReturnPointee(&pi));
    int*& rp=mock.getPointerRef();
    rp = p2;
    EXPECT_EQ(2,*pi);
    delete p2;

    int a=4;
    int b=5;
    EXPECT_CALL(mock,add()).Times(1).WillOnce(DoAll(Assign(&a,b),Return(1)));
    mock.add();
    EXPECT_EQ(5,a);
    Sequence s1, s2;
    EXPECT_CALL(mock, getS()).InSequence(s1, s2).WillOnce(ReturnNull());
    EXPECT_CALL(mock, add()).InSequence(s1).WillOnce(Return(6));
    mock.getS();
    mock.add();

    EXPECT_CALL(mock, setParam(_)).Times(AtLeast(1))
        .WillOnce(DoAll(SetArgumentPointee<0>(5),Return()));//Side effect
    mock.setParam(&a);
    EXPECT_EQ(5,a);

    ON_CALL(mock, add2(_)).WillByDefault(Return(120));
    ON_CALL(mock, add2(0)).WillByDefault(Return(10));
    ON_CALL(mock, add2(Gt(0))).WillByDefault(Return(20));
    EXPECT_CALL(mock,add2(_)).Times(AnyNumber());
    EXPECT_EQ(10,mock.add2(0));
    EXPECT_EQ(20,mock.add2(1));
    EXPECT_EQ(120,mock.add2(-1));
}