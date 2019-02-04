#include<iostream>
using namespace std;
struct M{
    M(){ cout<<"M ctor"<<endl;throw 1;}
    ~M(){cout<<"M dtor"<<endl;}
    void print(){cout<<"print"<<endl;}
};
int main(){
    M obj;
    obj.print();
    return 0;
}
//运行输出:
//M ctor
//terminate called after throwing an instance of 'int'
//Aborted
