#include <gtest/gtest.h>
#include <iostream>
using namespace std;
int Foo(int a, int b) {
    if (a == 0 || b == 0) {
        throw "don't do that";
    }
    int c = a % b;
    if (c == 0)
        return b;
    return Foo(b, c);
}

TEST(FooTest, Redirect) {
    EXPECT_EQ(6, Foo(30, 18));
    SUCCEED();
    EXPECT_EQ(3, Foo(4, 10)) << "Printed when error: Foo(4,10)";
}
TEST(FooTest, Fail) {
    FAIL();
}
TEST(FooTest, Throw) {
    EXPECT_ANY_THROW(Foo(0,0));
    EXPECT_LT(2,2);
}
TEST(FooTest, Str) {
    std::wstring wstrCoderZh = L"CoderZh";
    EXPECT_STREQ(L"CoderZh", wstrCoderZh.c_str());
}
TEST(FooTest, Continue) {
    ADD_FAILURE()<<"Sorry";
    cout<<"Continue\n";
}
TEST(FooTest, Throw2) {
    EXPECT_THROW(Foo(0,0), const char*);
}
bool Is5(int x,int y){return x+y==5;}
TEST(FooTest, Add) {
    int i=2,j=4;
    EXPECT_PRED2(Is5, i, j);
}
testing::AssertionResult AddFormat(
    const char* sm,
    const char* sn,
    const char* sk,
    int m,
    int n,
    bool k) {
    if (Is5(m, n))return testing::AssertionSuccess();
    testing::Message msg;
    msg << sm << " and " << sn << " 结果是 " << Is5(m, n) << " 实际是 " << sk;
    return testing::AssertionFailure(msg);
}
TEST(FooTest, AddFormat) {
    int i=2,j=4;
    EXPECT_PRED_FORMAT3(AddFormat, i, j, true);
}
/*
template <typename T> class FooType {
public:
    void Bar() { testing::StaticAssertTypeEq<int, T>(); }
};
TEST(TypeAssertionTest, Demo)
{
    FooType<bool> fooType;
    fooType.Bar();
}*/
class FooEnv:public testing::Environment {
public:
    virtual void SetUp() { cout << "===============setup\n"; }

    virtual void TearDown() { cout << "==============teardown\n"; }
};
int main(int argc, char* argv[]) {
    testing::AddGlobalTestEnvironment(new FooEnv);
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}