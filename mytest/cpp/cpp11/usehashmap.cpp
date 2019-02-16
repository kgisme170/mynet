#include<unordered_map>
#include<iostream>
using namespace std;

struct myhash {
    int operator()(const int &input) const {
        return input % 7;
    }
};
template<typename m>
void f(m& m1) {
    m1.insert(make_pair(1, 8));
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    for (int i = 0; i < 10; ++i) {
        m1.insert(make_pair(8, 9));
    }
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    m1.insert(make_pair(15, 19));
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    m1.insert(make_pair(1, 9));
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    m1.insert(make_pair(1, 10));
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    for (int i = 0; i < 20; ++i) {
        m1.insert(make_pair(i, i + 1));
    }
    auto p = m1.equal_range(1);
    for (auto i = p.first; i != p.second; ++i) {
        cout << '(' << i->first << ',' << i->second << ')';
    }
    cout << '\n';
    auto p2 = m1.equal_range(8);
    for (auto i = p2.first; i != p2.second; ++i) {
        cout << '(' << i->first << ',' << i->second << ')';
    }
    cout << '\n';
    m1.rehash(1);
    cout << "bucket_count()=" << m1.bucket_count() << '\n';
    cout << "bucket_size(0)=" << m1.bucket_size(0) << '\n';
    cout << "bucket_size(1)=" << m1.bucket_size(1) << '\n';
    cout << "bucket_size(2)=" << m1.bucket_size(2) << '\n';
    cout << "bucket_size(8)=" << m1.bucket_size(8) << '\n';
    for (auto i = 0; i <= 20; ++i) {
        cout << m1.bucket(i) << ',';
    }
    cout << '\n';
}

int main() {
    unordered_map<int, int, myhash> m1;
    f(m1);
    //map是
    //1个key值一个bucket,不同key相同hash的用下一个bucket
    cout << "==============\n";
    unordered_multimap<int, int, myhash> m2;
    f(m2);
    //multimap是
    //1.不同key相同hash的组成一个key的bucket(1和8的hash都是1)
    //2.同一个key不同值，组成第二层bucket
    return 0;
}