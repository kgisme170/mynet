#include<string>
#include<iostream>
using namespace std;
int main(){
    string a="Alibaba";
    string b=a;
    char* p=const_cast<char*>(a.c_str());
    *p='X';
    cout<<a<<endl;
    cout<<b<<endl;
    return 0;
}
