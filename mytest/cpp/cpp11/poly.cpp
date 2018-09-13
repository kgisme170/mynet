#include <iostream>
using namespace std;
struct B{
    size_t i = 2;
    virtual ~B() { cout << "B dtor\n"; }
};
struct D : B{
    size_t j = 3;
    size_t k = 4;
    size_t l = 5;
    ~D() { cout << "D dtor\n"; }//crash
};
void f(B* pb, size_t s){
    for (size_t i = 0; i < s; ++i)
        cout << pb[i].i<< endl;
}
int main(){
    cout << sizeof(B) << endl;
    cout << sizeof(D) << endl;
    B* p2 = new D[2];
    f(p2, 2);
    D buf[2];
    f(buf, 2);
    delete[] p2;
    return 0;
}