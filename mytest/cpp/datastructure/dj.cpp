#include<iostream>
#include<vector>
#include<map>
#include<queue>
using namespace std;

struct vertex{
    char _v;
    vertex(char v):_v(v){}
};
using element=pair<vertex, size_t>;
auto cmp=[](const element& e1, const element& e2){
    return e1.second<e2.second;
};
using Queue=priority_queue<element, std::vector<element>, decltype(cmp)>;
//typedef
class dj{//无向图Dijkstra
    Queue s;
    Queue u;
public:
};
int main(){

    return 0;
}
