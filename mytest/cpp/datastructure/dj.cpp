#include<iostream>
#include<vector>
#include<map>
#include<queue>
#include<cstdlib>
#include<unordered_map>
#include<unordered_set>
#include<tuple>
#include<limits>
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
struct E{
    char vertex;
    size_t distance;
    E(char v, size_t d):vertex(v),distance(d){}
    bool operator==(const E& e)const{
        return vertex < e.vertex;
    }
    void print()const{
        cout<<'('<<vertex<<',';
        if(distance>1000){
            cout<<'_';
        }else{
            cout<<distance;
        }
        cout<<')';
    }
};
/*
template<class C>
using Container = C<E>;
void print(const Container& c){
    for(auto& e : c)e.print();
}*/
template<>
struct std::hash<E> {
    std::size_t operator()(const E& e) const {
        return e.vertex;
    }
};

using edgeQueue=deque<E>;
using edgeMap=unordered_map<char, edgeQueue>;
using edgeSet=unordered_set<E>;
class graph{//dj{//无向图Dijkstra
    struct element{
        char from;
        char to;
        size_t distance;
        element(char f,char t,size_t d):
            from(f),to(t),distance(d){}
    };
    edgeMap mElements;
    edgeSet S, U;
    void print(){
        for(auto& s:S){s.print();}
        cout<<'\n';
        for(auto& u:U){u.print();}
        cout<<'\n';
    }
public:
    graph(const vector<edge>& v){
        for(const auto& e:v){
            mElements[get<0>(e)].push_back({get<1>(e),get<2>(e)});
            mElements[get<1>(e)].push_back({get<0>(e),get<2>(e)});
            U.insert(E(get<0>(e), numeric_limits<unsigned long>::max()));
        }
    }
    void calculate(char v){
        auto it = mElements.find(v);
        if(it == mElements.end()){
            cout<<"没有顶点"<<v<<endl;
            return;
        }

        S.insert(E(v,0));
        auto& q = it->second;
        for(const E& e:q){
            U.insert(e);
        }
        print();
    }
};
int main(){
    graph g(input);
    g.calculate('d');
    return 0;
}