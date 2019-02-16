struct A {
    int m_i;

    A() : m_i(1) {}

    A(int i) : m_i(i) {}
};
struct B {
    int m_i;

    B() : m_i(1) {}

    B(int i) : m_i(i) {}
};
struct C {
    int m_i;

    C() : m_i(1) {}

    C(int i) : m_i(i) {}
};
A Add(A&a, B&b){return A(a.m_i+b.m_i);}
B Add(B&b, A&a){return B(a.m_i+b.m_i);}
C Add(A&a, C&c){return C(a.m_i+c.m_i);}
template<class T1,class T2>
auto AddExtra(T1&& t1,T2&& t2)->decltype(Add(t1,t2)) {
    return Add(t1, t2);
}
int main() {
    AddExtra(A(2), B(3));//OK!
    AddExtra(A(4), C(5));//错误
    return 0;
}