#include <iostream>
using namespace std;
struct S {
    int m_i;
    S(int i):m_i(i){}
    bool operator < (const S& s){
        cout << "class function\n";
        return m_i < s.m_i;
    }
};
bool operator < (const S& s1, const S& s2) {
    cout << "global function\n";
    return s1.m_i < s2.m_i;
}
int main() {
    cout << boolalpha;
    S s0(1);
    const S s1(2), s2(3);
    cout<<(s0<s1)<<endl; // class
    cout<<(s1<s2)<<endl; // global
    S s3(2), s4(3);
    cout<<(s3<s4)<<endl; // class
    cout<<(s2<s3)<<endl; // global
    return 0;
}
