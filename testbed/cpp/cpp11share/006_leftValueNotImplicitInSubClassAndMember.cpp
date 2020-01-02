#include<iostream>
using namespace std;
struct Member {
    Member() { cout << "成员的默认构造" << endl; }

    Member(const Member &) { cout << "成员的拷贝构造" << endl; }
};
struct Base {
    Base() { cout << "基类的默认构造" << endl; }

    Base(const Base &) { cout << "基类的拷贝构造" << endl; }
};
struct D: Base {
    Member m_a;

    D() {}

    D(const D &) { cout << "继承类的拷贝构造" << endl; }
};
int main() {
    D d1;
    cout << endl;
    D d2(d1);
    return 0;
}
//打印输出:
//
//基类的默认构造
//成员的默认构造

//基类的默认构造
//成员的默认构造
//继承类的拷贝构造