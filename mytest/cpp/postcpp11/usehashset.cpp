#include<unordered_set>
#include<iostream>
#include<functional>
using namespace std;
struct node{
    size_t value;
    bool operator == (const node& n) const {return value == n.value;}
};
struct myhash{
    size_t operator()(const node& n){
        return (size_t)n.value;
    }
};
size_t h(const node& n){
    return n.value;
}
int main(){
    unordered_set<node, myhash> s;
    unordered_set<node, std::function<size_t(const node&)>> s2(3,h);
    unordered_set<node, std::function<size_t(const node&)>> s3(4,[](const node& e){return e.value;});
    cout<<s3.size()<<','<<s3.bucket_count()<<'\n';
    unordered_set<int> s1;
    s1.insert(1);
    s1.insert(9);
    s1.insert(0);
    s1.emplace(2);
    s1.emplace(3);
    s1.emplace(4);
    cout<<s1.bucket_count()<<'\n';
    cout<<s1.max_bucket_count()<<'\n';
    cout<<s1.load_factor()<<'\n';
    cout<<s1.max_load_factor()<<'\n';
    return 0;
}
