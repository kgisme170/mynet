#pragma once
#include<cstdio>
using namespace std;
struct M {
    int i;
    char *pc;

    M(int _i) : i(_i) {
        pc = new char[20];
        printf("M构造,&i=%p,pc=%p\n", &i, pc);
    }

    ~M() {
        printf("M析构,this指针=%p,i=%d,pc=%p\n", this, *(int *) this, pc);
        delete[] pc;
    }
};

struct N {
    static M obj;
};

void f1();
void f2();