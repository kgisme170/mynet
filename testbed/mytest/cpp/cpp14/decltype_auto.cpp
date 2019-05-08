#include<stdio.h>
struct M {
    M() : m_i(2) {}

    int m_i;

    int &GetRef() { return m_i; }

    decltype(auto) f1() {
        return GetRef();//f1返回值是int&
    }

    auto f2() {
        return GetRef();//f2返回值是int
    }
};
int main() {
    M obj;
    int &i = obj.f1();
    ++i;
    printf("m_i=%d\n", obj.m_i);
    //int& j=obj.f2();//会有编译错误
    return 0;
}