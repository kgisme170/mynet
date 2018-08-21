#include<vector>
#include<list>
#include<iostream>
#include<unordered_map>
using namespace std;

template <template <typename T, typename Alloc=std::allocator<T>> class T, typename Elem>
void f(){
    T<Elem> t;
    t.push_back(1);
}
struct myHash{
    int operator()(int i)const{return i%7;}
};
template <template <
    typename Key,
    typename T,
    class Hash = std::hash<Key>,
    class KeyEqual = std::equal_to<Key>,
    class Allocator = std::allocator< std::pair<const Key, T> >
> class MapT>
void g(){
    MapT<int, int, myHash> m;
    m.insert(make_pair(1,3));
    cout<<m.size()<<endl;
}
template<template<typename, typename> class C, typename E, typename A>
void h(const C<E,A>& container){
    cout<<container.size()<<endl;
}

int main(){
    f<vector, int>();
    f<list, long>();
    g<unordered_map>();
    vector<int> i(7);
    h(i);
    return 0;
}
