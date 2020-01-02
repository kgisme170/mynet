#include<memory>
#include<iostream>
using namespace std;
int i;
struct member {
    char *p;

    member() {
        cout << "member构造" << endl;
        p = new char[2017];
    }

    ~member() {
        cout << "member析构" << endl;
        delete[]p;
    }
};
struct N {
    member _m;

    N() {
        cout << "N ctor" << endl;
        if (i == 1) {
            throw 1;
        } else {
            cout << "No leak" << endl;
        }
    }

    ~N() { cout << "N dtor" << endl; }
};
int main(int argc,char*argv[]) {
    i = argc;
    N obj;
    return 0;
}