#include<set>
#include<vector>
#include<iostream>
#include<algorithm>
using namespace std;
/*功能: 克鲁斯卡尔算法*/
/*连同分量用set存储。图的存贮结构采用边集数组*/
struct edge{
    size_t v1;
    size_t v2;
    size_t weight;
    bool operator<(const edge& e){
        return weight<e.weight;
    }
};
void kruskal(edge* const e, size_t len){
    set<size_t> vSet;//所有顶点的集合
    for(size_t i=0;i<len;++i){
        vSet.insert(e[i].v1);
        vSet.insert(e[i].v2);
    }
    set<set<size_t>> connectedComponents;//已经有的连通分量
    for(auto v:vSet){
        set<size_t> s;
        s.insert(v);
        connectedComponents.insert(s);
    }//初始状态，每个顶点都是一个连通分量

    //寻找不属于同一个连通分量的代价最小的边
    auto first = min_element(e,e+len);
}
int main(){
    edge e[]={
        {0,2,1},
        {0,1,6},
        {0,3,5},
        {1,2,5},
        {1,4,3},
        {2,3,5},
        {2,4,6},
        {2,5,4},
        {4,5,6}
    };
    kruskal(e, sizeof(e)/sizeof(e[0]));
    return 0;
}