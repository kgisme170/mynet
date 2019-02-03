#include<algorithm>
#include<iostream>
#include<iterator>
#include<vector>
using namespace std;
int main(){
    vector<int> v1={1,2,3,4};
    vector<int> v2={2,3};
    vector<int> vout;
    set_difference(v1.begin(),v1.end(),v2.begin(),v2.end(),back_inserter(vout));
    for(auto i:vout)cout<<i<<endl;
    return 0;
}