#include<iostream>
#include<vector>
using namespace std;
class C{
    C(const C&c){cout<<"copy\n";}
public:
    int m_i;
    C():m_i(-1){}
};
int main(){
    const vector<C> v(3);
    for(auto& c:v){
        cout<<c.m_i<<'\n';
    }
    return 0;
}
