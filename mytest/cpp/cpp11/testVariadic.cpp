#include<iostream>
using namespace std;
template<typename T>
void printAll(T&& value){
    cout<<value<<',';
}
template<typename Head, typename...Tail>
void printAll(Head&& h, Tail&&...tail){
    cout<<h<<',';
    printAll(forward<Tail>(tail)...);
}
template<typename T>
T sumAll(T&& value){
    return value;
}
template<typename Head, typename...Tail>
Head sumAll(Head&& h, Tail&&...tail){
    return h+sumAll(forward<Tail>(tail)...);
}
/*
template<typename Head, typename...Tail>
Head sum(Head&&h, Tail&&... tail){
    Head r=h;
    (int[]){(r+=tail,0)...};
    return r;
}
*/
//template<size_t index, typename...Tail>//模板
//struct typeList;
template<size_t index, typename Head, typename...Tail>
struct typeList{//特化1
    using type=typename typeList<index-1, Tail...>::type;
};
template<typename Head, typename... Tail>
struct typeList<0,Head,Tail...>{//特化，终止条件
    using type=Head;
};

int main(){
    printAll(1,2,"xyz",3);
    cout<<'\n'<<sumAll(2L,'a',3);
    //cout<<'\n'<<sum(2,3,4)<<endl;
    typeList<1,int,short,char*>::type x=3;
    return 0;
}
