#include<vector>
#include<iostream>
using namespace std;
template<template<typename, typename> class C, typename E, typename A>
void f(const C<E,A>& container){
    cout<<container.size()<<endl;
}
int main(){
    vector<int> i(7);
    f(i);
    return 0;
}
