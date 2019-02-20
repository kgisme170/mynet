#include <gtest/gtest.h>
#include <iostream>
using namespace std;
bool IsEven(int i) {
    return i % 2 == 0;
}
class IsEvenParamTest:public testing::TestWithParam<int>{};
// 4 cases
INSTANTIATE_TEST_CASE_P(MySuccess,IsEvenParamTest,testing::Values(2,4,6,8));
TEST_P(IsEvenParamTest, All) {
    int n = GetParam();
    EXPECT_TRUE(IsEven(n));
}

// 3 typed cases
template<typename T>
class FooTest:public testing::Test {
public:
    T m_data;
};
typedef testing::Types<char, int, unsigned int> MyTypes;
TYPED_TEST_CASE(FooTest, MyTypes);
TYPED_TEST(FooTest, Case) {
    TypeParam n=this->m_data;
    ASSERT_EQ(n+1, this->m_data+1);
}
TYPED_TEST_CASE_P(FooTest);

// 2*3 typed cases
TYPED_TEST_P(FooTest, case1) {
    TypeParam n=this->m_data;
    ASSERT_EQ(n+1, this->m_data+1);
}
TYPED_TEST_P(FooTest, case2) {
    TypeParam n=this->m_data;
    ASSERT_EQ(n+2, this->m_data+2);
}
REGISTER_TYPED_TEST_CASE_P(FooTest, case1, case2);
typedef testing::Types<char, int, unsigned int> MyTypes_P;
INSTANTIATE_TYPED_TEST_CASE_P(My, FooTest, MyTypes_P);

void core() {
    cerr << "core dump";
    int *p = 1;
}
void die() {
    cerr << "Existed with code 1";
    _exit(1);
}
TEST(a,b) {
    EXPECT_DEATH(core(), "core dump");
    EXPECT_EXIT(die(), testing::ExitedWithCode(1), "Existed with code 1");
}

int DieInDebugElse1(int* sideeffect) {
    if (sideeffect) *sideeffect = 1;
#ifndef NDEBUG
    GTEST_LOG_(INFO)<<"debug death inside DieInDebugElse12()";
#endif  // NDEBUG
    return 12;
}
TEST(a,c){
    int sideeffect = 0;
    EXPECT_DEBUG_DEATH(DieInDebugElse1(&sideeffect), "death");
}

TEST(MyDeathTest, TestOne) {
  testing::FLAGS_gtest_death_test_style = "threadsafe";
  // This test is run in the "threadsafe" style:
  ASSERT_DEATH(core(), "");
}

TEST(MyDeathTest, TestTwo) {
  // This test is run in the "fast" style:
  ASSERT_DEATH(core(), "");
}
int main(int argc, char* argv[]) {
    testing::GTEST_FLAG(output) = "xml:";
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
