#include<algorithm>
#include<iostream>
#include<vector>
#include<map>
#include<queue>
#include<cstdlib>
#include<unordered_map>
#include<unordered_set>
#include<set>
#include<tuple>
#include<limits>
using namespace std;
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
    E(const E& e):vertex(e.vertex),distance(e.distance){}
    E& operator=(const E& e){
        vertex = e.vertex;
        distance = e.distance;
        return *this;
    }
    bool operator==(const E& e)const{//set的检查
        return vertex == e.vertex;
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
template<>
struct std::hash<E> {
    std::size_t operator()(const E& e) const {
        return e.vertex;
    }
};
bool operator<(const E& e1,const E& e2){
    return e1.distance < e2.distance;
}

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
    edgeMap mElements;//边的集合
    edgeSet S, U;
    unordered_set<char> vSet;//顶点集合
    void print(){
        cout<<"----------\n";
        for(auto& s:S){s.print();}
        cout<<'\n';
        for(auto& u:U){u.print();}
        cout<<'\n';
    }
public:
    graph(const vector<edge>& v){
        for(const auto& e:v){
            auto& v0=get<0>(e);
            auto& v1=get<1>(e);
            auto& d=get<2>(e);

            mElements[v0].push_back({v1,d});
            mElements[v1].push_back({v0,d});
            vSet.insert(v0);
            vSet.insert(v1);
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
        cout<<v<<"的邻接顶点个数="<<q.size()<<endl;
        for(E& e:q){
            U.insert(e);
        }
        for(char c:vSet){
            if(c==v)continue;
            if(U.find(E(c,0))==U.end()){
                cout<<"不相邻顶点"<<c<<endl;
                U.insert(E(c,numeric_limits<unsigned long>::max()));
            }
        }
        print();

        while(!U.empty()){
            cout<<"\n进入while\n";
            set<E> se(U.begin(),U.end(),less<E>());//U的元素进行排序
            auto small = se.begin();
            small->print();
            S.insert(*small);
            U.erase(*small);
            print();
            //c的所有邻近点
            cout<<"更新距离\n";
            auto& q = mElements[small->vertex];
            for(const E& e: q){
                if (S.find(e)!=S.end()){
                    continue;
                }
                auto newDistance = e.distance + small->distance;
                auto p = U.find(e);
                auto oldDistance = p->distance;
                if(newDistance<oldDistance){
                    U.erase(e);
                    U.insert(E(e.vertex, newDistance));
                }
            }
            print();
        }
    }
};
int main(){
    graph g(input);
    g.calculate('d');
    return 0;
}