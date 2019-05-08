#include<stdio.h>
struct M {
    int m_i;

    M() : m_i(2) {}

    int GetValue() { return m_i; }

    int &GetRef() { return m_i; }
};
int main() {
    M obj;
    int i = obj.GetValue();
    int j = obj.GetRef();//不获取&
    int &k = obj.GetRef();//获取&

    ++i;
    ++j;
    printf("%d\n", obj.m_i);//打印2
    ++k;
    printf("%d\n", obj.m_i);//打印3

    return 0;
}