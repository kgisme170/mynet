#include<string>
#include<iostream>
using namespace std;
struct S{
    string name;
    S(){name = "S"; cout<<"ctor\n";}
    ~S(){cout<<"dtor\n";}
}obj;
const string name = "zealot";
string f(){
    return name + "f" + obj.name;
}
string g(){
    return name + "g" + obj.name;
}
