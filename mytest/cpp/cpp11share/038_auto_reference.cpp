#include<stdio.h>
#include<iterator>
struct M{
    int m_i;
    M():m_i(2){}
    M(const M&){printf("拷贝\n");}
};
int main(){
    int buf[]={4,3,2,1};
    for(auto it=std::begin(buf);it!=std::end(buf);++it)//range based for的实质
        ++*it;

    for(auto i:buf) printf("%d,",i);//range based for遍历数组
    printf("\n^^^\n");

    for(auto i:buf) ++i;//拷贝, 没有真的+1
    for(auto i:buf) printf("%d,",i);

    printf("\n^^^\n");
    for(auto&i:buf) ++i;//引用, 真的+1了
    for(auto i:buf) printf("%d,",i);
    return 0;
}