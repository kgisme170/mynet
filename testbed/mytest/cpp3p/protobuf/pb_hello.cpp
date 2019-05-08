#include "hello.pb.h"
#include<fstream>
#include<iostream>
using namespace std;
int main() {
    cout << "start\n";
    fstream fo("./hello.data", ios::binary | ios::out);
    hello p1, p2, p3;
    cout << "p1.set\n";
    p1.set_f1(1);
    p1.set_f2(2);
    cout << "p2.set\n";
    p2.set_f1(3);
    p2.set_f2(4);
    cout << "p3.set\n";
    p3.set_f1(5);
    p3.set_f2(6);
    cout << "serialize\n";
    p1.SerializeToOstream(&fo);
    p2.SerializeToOstream(&fo);
    p3.SerializeToOstream(&fo);//只有最后一次的SerializeToStream在fo里面有效，之前的都是覆盖。
    fo.close();

    cout << "parsefrom\n";
    fstream fi("./hello.data", ios::binary | ios::in);
    hello pi;
    pi.ParseFromIstream(&fi);
    cout << pi.f1() << pi.f2() << endl;
    return 0;
}