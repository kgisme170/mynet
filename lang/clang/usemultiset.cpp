#include<unordered_set>
#include<iostream>
struct big{
    long l;
    long v;
    big(long _l,long _v):l(_l),v(_v){}
};
struct myhash{
    size_t operator()(const big& b)const{return b.l;}
};
bool operator==(const big&b1, const big&b2){
    return b1.l==b2.l;
}
using namespace std;
int main(){
    unordered_multiset<int> s1;
    s1.insert(1);
    s1.insert(1);
    s1.insert(1);
    s1.emplace(1);
    s1.emplace(3);
    s1.emplace(4);
    cout<<"bucket_count="<<s1.bucket_count()<<'\n';
    cout<<"size="<<s1.size()<<'\n';
    cout<<s1.max_bucket_count()<<'\n';
    cout<<s1.load_factor()<<'\n';
    cout<<s1.max_load_factor()<<'\n';
    unordered_multiset<long> s2;
    cout<<"s2 max_bucket_count="<<s2.max_bucket_count()<<'\n';
    unordered_multiset<big, myhash> s3;
    cout<<"s3 max_bucket_count="<<s3.max_bucket_count()<<'\n';
    s3.insert(big(1,13));
    s3.insert(big(1,23));
    s3.insert(big(1,33));
    auto p = s3.count(big(1,3));
    cout<<p<<'\n';
    auto r = s3.equal_range(big(1,3));
    for(auto it = r.first;it!=r.second;++it){
        cout<<it->v<<endl;
    }
    return 0;
}
