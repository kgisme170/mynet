#include<sstream>
#include<map>
#include<string>
#include<iostream>
using namespace std;
template<typename K, typename V>
string printMap(const map<K,V>& m){
    stringstream s;
    for(typeof(m.begin())it=m.begin();it!=m.end();++it){
        s<<"key="<<it->first<<",value="<<it->second<<"|";
    }
    return s.str();
}
int main(){
    map<string, uint32_t> msi;
    msi.insert(make_pair("xyz",1));
    msi.insert(make_pair("abc",2));
    cout<<printMap(msi)<<endl;
    return 0;
}
