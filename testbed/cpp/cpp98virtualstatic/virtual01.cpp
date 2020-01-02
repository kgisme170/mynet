#include<stdio.h>
struct B {
    B() { printf("构造\n"); }

    virtual void f() const { printf("Base\n"); }
};
struct D : B {
    void f() const { printf("Derived\n"); }
};

void Keng1(B b) {
    b.f();
}
void Keng2(const B& b) {
    b.f();
}

int main() {
    D d;
    B b = d;
    B &rb = d;
    printf("Keng1--------------\n");
    Keng1(d);  //1.打印什么
    Keng1(b);  //2.打印什么
    Keng1(rb); //3.打印什么

    printf("Keng2--------------\n");
    Keng2(d);  //4.打印什么
    printf("还会有新对象吗?\n");
    Keng2(b);  //5.打印什么
    Keng2(rb); //6.打印什么
    return 0;
}