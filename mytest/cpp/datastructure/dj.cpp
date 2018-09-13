#include<iostream>
#include<vector>
#include<map>
#include<queue>
#include<cstdlib>
#include<initializer_list>
#include<tuple>
using namespace std;
/*
struct vertex{
    char _v;
    vertex(char v):_v(v){}
};
using element=pair<vertex, size_t>;
auto cmp=[](const element& e1, const element& e2){
    return e1.second<e2.second;
};
using Queue=priority_queue<element, std::vector<element>, decltype(cmp)>;
*/

using edge=tuple<char,char,size_t>;
vector<edge> input={
    {'a','b',12},
    {'a','f',16},
    {'a','g',14},
    {'b','c',10},
    {'b','f',7},
    {'c','d',3},
    {'c','e',5},
    {'c','f',6},
    {'d','e',4},
    {'e','f',2},
    {'e','g',8},
    {'f','g',9}
};
class graph{//dj{//无向图Dijkstra
    struct element{
        char from;
        char to;
        size_t distance;
        element(char f,char t,size_t d):
            from(f),to(t),distance(d){}
    };
    vector<element> mElements;
public:
    graph(const vector<edge>& v){
        for(const auto& e:v){
            mElements.emplace_back(element(get<0>(e),get<1>(e),get<2>(e)));
            mElements.emplace_back(element(get<1>(e),get<0>(e),get<2>(e)));
        }
    }
};
int main(){
    return 0;
}