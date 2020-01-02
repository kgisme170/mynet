#include<iostream>
#include<map>
#include<set>
#include<deque>
using namespace std;
struct edge {//邻接表的边
    int nodeNum;
    edge *next;

    edge() : nodeNum(-1), next(NULL) {}
};
class vertex {
    edge *head;
    edge *tail;

    void add(edge *e) {
        if (head == NULL) {
            head = tail = e;
        } else {
            tail->next = e;
            tail = e;
        }
    }

public:
    vertex() : head(NULL), tail(NULL) {}

    void add(int nodeNum) {
        edge *e = new edge();
        e->nodeNum = nodeNum;
        add(e);
    }

    void remove(int nodeNum) {
        edge *p = head;
        edge *pre = NULL;
        while (p) {
            if (p->nodeNum == nodeNum) {
                if (p == head) {
                    if (head == tail) {
                        head = tail = NULL;
                    } else {
                        head = head->next;
                    }
                } else {
                    pre->next = p->next;
                }
                return;
            }
            pre = p;
            p = p->next;
        }
    }

    void print() const {
        edge *p = head;
        while (p) {
            cout << "-->" << p->nodeNum;
            p = p->next;
        }
    }

    bool hasEdge(int nodeNum) {
        edge *p = head;
        while (p) {
            if (p->nodeNum == nodeNum) {
                return true;
            }
            p = p->next;
        }
        return false;
    }
};
class graph {
    map<int, vertex> vMap;
    set<int> vSet;//保存所有定点
public:
    graph(int input[][2], size_t len) {
        for (size_t i = 0; i < len; ++i) {
            int vNum = input[i][0];
            int vEdge = input[i][1];
            vMap[vNum].add(vEdge);
            vSet.insert(vNum);
            vSet.insert(vEdge);
        }
    }

    void print() {
        for (auto p:vMap) {
            cout << p.first;
            const vertex &v = p.second;
            v.print();
            cout << '\n';
        }
    }

    void topologicalSort() {
        deque<int> result;
        while (!vMap.empty()) {
            deque<int> keys;
            for (auto p:vMap) {
                keys.push_back(p.first);
            }
            for (int k:keys) {
                bool findInEdge = false;
                for (auto p:vMap) {
                    if (k == p.first) {
                        //cout<<"Don't compare\n";
                        continue;//不和自己比较
                    }
                    //cout<<"check "<<k<<'\n';
                    if (p.second.hasEdge(k)) {
                        findInEdge = true;
                        break;
                    }
                }
                if (!findInEdge) {//入度为0
                    //cout<<"key="<<k<<", Indegree=0, remove it\n";
                    vMap.erase(k);
                    vSet.erase(k);
                    result.push_back(k);
                }
            }
        }
        for (int s:vSet) {
            result.push_back(s);
        }
        vSet.clear();
        for (int r:result) {
            cout << r << ',';
        }
        cout << '\n';
    }
};
int main() {
    int input[][2] = {
            {1, 7},
            {2, 1},
            {2, 4},
            {3, 6},
            {3, 7},
            {4, 5},
            {4, 6}
    };
    graph g(input, 7);
    g.print();
    g.topologicalSort();
    return 0;
}