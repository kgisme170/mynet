#include <gtest/gtest.h>
bool IsEven(int i){return i%2==0;}
class IsEvenParamTest:public testing::TestWithParam<int>{};
// 4 cases
INSTANTIATE_TEST_CASE_P(MySuccess,IsEvenParamTest,testing::Values(2,4,6,8));
TEST_P(IsEvenParamTest, All){
    int n = GetParam();
    EXPECT_TRUE(IsEven(n));
}

// 3 typed cases
template<typename T>
class FooTest:public testing::Test{
public:
    T m_data;
};
typedef testing::Types<char, int, unsigned int> MyTypes;
TYPED_TEST_CASE(FooTest, MyTypes);
TYPED_TEST(FooTest, Case){
    TypeParam n=this->m_data;
    ASSERT_EQ(n+1, this->m_data+1);
}
TYPED_TEST_CASE_P(FooTest);

// 2*3 typed cases
TYPED_TEST_P(FooTest, case1){
    TypeParam n=this->m_data;
    ASSERT_EQ(n+1, this->m_data+1);
}
TYPED_TEST_P(FooTest, case2){
    TypeParam n=this->m_data;
    ASSERT_EQ(n+2, this->m_data+2);
}
REGISTER_TYPED_TEST_CASE_P(FooTest, case1, case2);
typedef testing::Types<char, int, unsigned int> MyTypes_P;
INSTANTIATE_TYPED_TEST_CASE_P(My, FooTest, MyTypes_P);
