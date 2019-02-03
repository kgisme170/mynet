#include<iostream>
using namespace std;
class S{
    int m_i;
    int m_j;
public:
    S(int i):m_i(i),m_j(i){cout<<"ctor\n";}
    void print(){cout<<m_i<<','<<m_j<<'\n';}
};
void f(const S& s){}
int main(){
    f(1);
    return 0;
}