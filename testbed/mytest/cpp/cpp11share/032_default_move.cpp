#include<cstdio>
#include<utility>
using namespace std;
struct Base {
    Base() { printf("Base\n"); }

    Base(const Base &) { printf("Base copy ctor\n"); }

    Base(Base &&) { printf("Base move ctor\n"); }
};
struct Derived:Base {
    Derived() {}

    Derived(const Derived &d) : Base(d) { printf("Derived copy ctor\n"); }

    Derived(Derived &&) = default;//注意这里
};
struct Other:Base {
    Other() {}

    Other(const Other &o) : Base(o) { printf("Other copy ctor\n"); }

    Other(Other &&) {};//什么都不干
};
int main() {
    Derived d;
    Derived d2 = d;
    Derived d3 = move(d);
    printf("=============\n");
    Other o;
    Other o2 = o;
    Other o3 = move(o);
    return 0;
}
//运行输出
//Base
//Base copy ctor
//Derived copy ctor
//Base move ctor//这里调用了move ctor
//=============
//Base
//Base copy ctor
//Other copy ctor//这里调用了copy ctor
//Base
//
//{}的空函数版本，相当于用拷贝的方式初始化基类，而=default的版本，编译器分析这是move ctor，会调用相应的Base(Base&&)，完成移动语义。

