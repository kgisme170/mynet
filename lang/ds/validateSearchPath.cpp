#include <iostream>
#include <vector>
using namespace std;
/*功能:判定一个关键字序列是否是排序二叉树的排序查找序列
 * 由于查找路径上所有的数都是趋近于待查找的结果x
 * 因此大于x的数字应构成单调下降序列，小于x的数字应该构成单调上升序列
 */
bool judge(int a[], size_t len, int x){
    vector<int> greater;
    vector<int> less;
    for(size_t i=0;i<len;++i){
        if(a[i]>x)greater.push_back(a[i]);
        else if(a[i]<x) less.push_back(a[i]);
        else cout<<"编程错误\n";
    }
    for(size_t i=0;i<greater.size()-1;++i){
        //cout<<"greater"<<greater[i]<<','<<greater[i+1]<<'\n';
        if(greater[i]<greater[i+1])return false;
    }
    for(size_t i=0;i<less.size()-1;++i){
        //cout<<"less"<<less[i]<<','<<less[i+1]<<'\n';
        if(less[i]>less[i+1])return false;
    }
    return true;
}
int main(){
    int a1[]={46,36,18,28};//ok
    int a2[]={15,25,55};//ok
    int a3[]={28,36,18,46};//fail
    cout<<boolalpha;
    cout<<judge(a1,4,35)<<'\n';
    cout<<judge(a2,3,35)<<'\n';
    cout<<judge(a3,4,35)<<'\n';
    return 0;
}
