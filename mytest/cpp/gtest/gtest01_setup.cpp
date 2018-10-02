#include <gtest/gtest.h>
#include <iostream>
#include <typeinfo>
using namespace std;
int Foo(int a, int b)
{
    if (a == 0 || b == 0)
    {
        throw "don't do that";
    }
    int c = a % b;
    if (c == 0)
        return b;
    return Foo(b, c);
}
class FooTest : public testing::Test {
protected:
    static void SetUpTestCase() {
        cout<<"FooTest SetUp\n";
    }
    static void TearDownTestCase() {
        cout<<"FooTest TearDown\n";
    }
};
class FooTest2: public FooTest{
protected:
    static void SetUpTestCase() {
        cout<<"FooTest2 SetUp\n";
    }
    static void TearDownTestCase() {
        cout<<"FooTest2 TearDown\n";
    }
    virtual void SetUp(){cout<<"case setup\n";}
    virtual void TearDown(){cout<<"case teardown\n";}
};
TEST_F(FooTest, HandleNoneZeroInput)
{
    EXPECT_EQ(2, Foo(4, 10));
    EXPECT_EQ(6, Foo(30, 18));
}
TEST_F(FooTest, Case2)
{
    EXPECT_EQ(2, Foo(4, 10));
    EXPECT_EQ(6, Foo(30, 18));
    cout<<typeid(*this).name()<<endl;
}
TEST_F(FooTest2, Success){
    ASSERT_TRUE(true);
}