#include<iostream>
#include<type_traits>
using namespace std;
template<class T>void f(T obj){
    cout<<is_reference<T>::value<<endl; // false
    cout<<is_reference<decltype(obj)>::value<<endl; //false
    obj+=1;}
template<class T>void g(T&obj){
    cout<<is_reference<T>::value<<endl; //false
    cout<<is_reference<decltype(obj)>::value<<endl; //true
    obj+=10;}
template<class T>void h(T&&obj){
    cout<<is_reference<T>::value<<endl; //true
    cout<<is_reference<decltype(obj)>::value<<endl; //true
    obj+=100;}
int main()
{
    cout<<boolalpha;
    int n=0;
    int&i=n;
    f(i);
    g(i);
    h(i);
    cout<<"i="<<i<<endl;
    cout<<is_reference<decltype(i)>::value<<endl;
    cout<<is_reference<decltype(n)>::value<<endl;
    return 0;
}