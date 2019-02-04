#include<iostream>
using namespace std;

template<size_t index, class ... Tail>
struct typeList;

template<size_t index, class Head, class ... Tail>//long match
struct typeList<index, Head, Tail...>
{
    using type = typename typeList<index-1, Tail...>::type;
};

template<class Head, class ... Tail> // specialization of long match
struct typeList<0, Head, Tail...>
{
    using type = Head;
};

int main()
{
    typeList<2, int, short, char*, int*>::type x="hello";
    cout<<x<<endl;
    return 0;
}