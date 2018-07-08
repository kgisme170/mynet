#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
using namespace std;
// 反转一个顺序表
typedef int T;
typedef vector<T> Sqlist;
void removeIndex(Sqlist& l,size_t from, size_t to){
    if(from <0 || to < 0 || from > to || from >= l.size())return;
    for(size_t i=0;i<l.size()-to-1;++i){
        cout<<from+i<<'\n';
        l[from+i]=l[to+i+1];
    }
}
int main()
{
    T buf[]={6,-1,7,0,3,2,9,11};
    Sqlist v(buf,&buf[8]);

    copy(v.begin(),v.end(),ostream_iterator<T>(cout, ","));
    cout<<"\n";
    removeIndex(v,2,3);
    v.resize(v.size()-2);
    copy(v.begin(),v.end(),ostream_iterator<T>(cout, ","));
    return 0;
}
