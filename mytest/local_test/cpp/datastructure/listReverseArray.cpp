#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
using namespace std;
// 反转一个顺序表
typedef int T;
typedef vector<T> Sqlist;
void reverse(Sqlist& l){
    size_t i=0;
    size_t j=l.size()-1;
    int temp;
    for(;i<j;++i,--j){
        temp=l[i];
        l[i]=l[j];
        l[j]=temp;
    }
}
int main()
{
    T buf[]={6,-1,7,0,3,2};
    Sqlist v(buf,&buf[6]);

    copy(v.begin(),v.end(),ostream_iterator<T>(cout, ","));
    cout<<"\n";
    reverse(v);
    copy(v.begin(),v.end(),ostream_iterator<T>(cout, ","));
    return 0;
}
