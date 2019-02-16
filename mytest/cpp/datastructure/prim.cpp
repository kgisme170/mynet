#include <iostream>
#include <list>
#include <limits.h>
#include <algorithm>
#include <vector>
using namespace std;
struct edge {
    size_t v1;
    size_t v2;
    int weight;
};
/*功能: prim算法 采用邻接矩阵存储*/
int matrix[6][6]= {
        {INT_MAX, 6,       1,       5,       INT_MAX, INT_MAX},
        {6,       INT_MAX, 5,       INT_MAX, 3,       INT_MAX},
        {1,       5,       INT_MAX, 5,       6,       4},
        {5,       INT_MAX, 5,       INT_MAX, INT_MAX, 2},
        {INT_MAX, 3,       6,       INT_MAX, INT_MAX, 6},
        {INT_MAX, INT_MAX, 4,       2,       6,       INT_MAX}
};
void prim(const vector<vector<int>> graph) {
    list <size_t> vSelected;//已经选入的顶点集合
    list <size_t> vOthers;//尚未选入的顶点集合
    vSelected.push_back(0);
    for (size_t i = 1; i < graph.size(); ++i) {
        vOthers.push_back(i);
    }
    list <edge> eList;
    while (!vOthers.empty()) {
        //考察两个顶点集合之间最短的边
        size_t path = INT_MAX;
        size_t iSelected = INT_MAX;
        size_t iOther = INT_MAX;
        for (size_t s:vSelected) {
            for (size_t o:vOthers) {
                size_t dist = graph[s][o];
                if (dist < path) {
                    path = dist;
                    iSelected = s;
                    iOther = o;
                }
            }
        }
        cout << "edge:" << iSelected + 1 << "," << iOther + 1 << '\n';
        edge e = {.v1=iSelected + 1, .v2=iOther + 1, .weight=graph[iSelected][iOther]};
        eList.push_back(e);
        vSelected.push_back(iOther);
        vOthers.remove(iOther);
    }
    int sum = 0;
    for (auto e:eList) {
        cout << e.v1 << "-->" << e.v2 << '\n';
        sum += e.weight;
    }
    cout << "总代价=" << sum << '\n';
}
int main() {
    vector <vector<int>> graph;//graph必须是连通图
    graph.push_back({INT_MAX, 6, 1, 5, INT_MAX, INT_MAX});
    graph.push_back({6, INT_MAX, 5, INT_MAX, 3, INT_MAX});
    graph.push_back({1, 5, INT_MAX, 5, 6, 4});
    graph.push_back({5, INT_MAX, 5, INT_MAX, INT_MAX, 2});
    graph.push_back({INT_MAX, 3, 6, INT_MAX, INT_MAX, 6});
    graph.push_back({INT_MAX, INT_MAX, 4, 2, 6, INT_MAX});
    prim(graph);
    return 0;
}