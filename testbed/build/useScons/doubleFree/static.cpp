#include "static.h"
#include <stdio.h>
class TestClass {
    int* m_p;
public:
    TestClass() {
        printf("TestClass ctor %p %p\n", this, m_p);
        m_p = new int[10];
        printf("TestClass ctor %p\n", m_p);
    }
    ~TestClass() {
        printf("TestClass dtor %p %p\n", this, m_p);
        delete [] m_p;
    }

    void set(int index, int value) {
        m_p[index] = value;
    }
}test_var;

void set(int index, int value) {
    test_var.set(index, value);
}
