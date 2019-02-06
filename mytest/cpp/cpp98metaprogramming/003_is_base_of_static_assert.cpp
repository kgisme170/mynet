#include<iostream>
using namespace std;

template<bool b>
void STATIC_ASSERT_TRUE();
template<>
void STATIC_ASSERT_TRUE<true>(){
    int i;
}

template<bool b>
bool TEST_TRUE(){return sizeof(STATIC_ASSERT_TRUE<b>::i)==sizeof(int);}

int main()
{
    TEST_TRUE<1==1>();
     // TEST_TRUE<1==0>();//编译错误: error: no member named 'i' in 'STATIC_ASSERT_TRUE<false>'
    return 0;
}