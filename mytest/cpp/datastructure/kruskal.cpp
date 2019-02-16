#include<set>
#include<vector>
#include<iostream>
#include<algorithm>
#include<iterator>
using namespace std;
/*功能: 克鲁斯卡尔算法*/
/*连同分量用set存储。图的存贮结构采用边集数组*/
struct edge {
    size_t v1;
    size_t v2;
    size_t weight;

    bool operator<(const edge &e) const {
        return weight < e.weight;
    }
};
struct connectedComponents {//连通分量
    set <set<size_t>> data;

    connectedComponents(const set <size_t> &vSet) {
        for (auto v:vSet) {
            set <size_t> s;
            s.insert(v);
            data.insert(s);
        }//初始状态，每个顶点都是一个连通分量
    }

    bool connect(size_t v1, size_t v2) {//增加一个连通分量，边的形式
        //cout<<"尝试连接v1="<<v1<<",v2="<<v2<<'\n';
        bool fV1 = false;
        bool fV2 = false;
        set <size_t> d1, d2;
        for (auto d:data) {
            bool f1 = d.find(v1) != d.end();
            bool f2 = d.find(v2) != d.end();
            if (f1 && f2) {//该分量的两个顶点同时在某个连通分量里面，舍弃
                //cout<<"舍弃\n";
                return false;
            }
            if (f1) {
                fV1 = true;
                d1 = d;
            }
            if (f2) {
                fV2 = true;
                d2 = d;
            }
        }

        set <size_t> s1;
        s1.insert(v1);
        set <size_t> s2;
        s2.insert(v2);
        bool findV1 = data.find(s1) != data.end();
        bool findV2 = data.find(s2) != data.end();
        if (findV1 && findV2) {//两个独立的点，作为连通分量
            data.erase(s1);
            data.erase(s2);
            set <size_t> _;
            _.insert(v1);
            _.insert(v2);
            data.insert(_);//放一条新的边进去
            //cout<<"case1，增加新边\n";
            return true;
        }
        if (fV1 && fV2) {
            //两个顶点分别在两个不同的连通分量里面
            set <size_t> n;
            n.insert(d1.begin(), d1.end());
            n.insert(d2.begin(), d2.end());
            data.erase(d1);
            data.erase(d2);
            data.insert(n);
            //cout<<"case0, 连接两个连通量\n";
            return true;
        }
        if (findV1 && !findV2) {//V2在某个连通分量里面，找到包含V2的连通分量，把V1加进去
            for (auto d:data) {
                if (d.find(v2) != d.end()) {//该分量的两个顶点已经在某个连通分量里面，舍弃
                    d.insert(v1);//添加顶点V1
                    break;
                }
            }
            data.erase(s1);//去处单独作为连通分量的v1
            //cout<<"case2，v1加入连通分量:"<<v1<<'\n';
            return true;
        }
        if (!findV1 && findV2) {//V1在某个连通分量里面，找到包含V1的连通分量，把V2加进去
            for (auto d:data) {
                if (d.find(v1) != d.end()) {//该分量的两个顶点已经在某个连通分量里面，舍弃
                    d.insert(v2);//添加顶点V2
                    break;
                }
            }
            data.erase(s2);//去处单独作为连通分量的v2
            //cout<<"case3，v2加入连通分量:"<<v2<<'\n';
            return true;
        }
        //v1和v2都没有被找到
        cerr << "编程错误\n";
        return false;
    }

    bool end() {
        return data.size() == 1;
    }
};
void kruskal(edge* const e, size_t len) {
    set <size_t> vSet;//所有顶点的集合
    for (size_t i = 0; i < len; ++i) {
        vSet.insert(e[i].v1);
        vSet.insert(e[i].v2);
    }
    connectedComponents cc(vSet);
    //寻找不属于同一个连通分量的代价最小的边
    vector <edge> ve(e, e + len);
    sort(ve.begin(), ve.end());
    cout << boolalpha;
    size_t sum = 0;
    vector <edge> result;
    for (auto e:ve) {
        bool b = cc.connect(e.v1, e.v2);
        if (b) {
            sum += e.weight;
            result.push_back(e);
        }
        //cout<<"连接结果="<<b<<'\n';
        if (cc.end())break;
    }
    cout << "最小代价=" << sum << '\n';
    for (auto e:result) {
        cout << e.v1 << "-->" << e.v2 << '=' << e.weight << '\n';
    }
}
int main() {
    edge e[] = {
            {0, 1, 6},
            {0, 2, 1},
            {0, 3, 5},
            {1, 2, 5},
            {1, 4, 3},
            {2, 3, 5},
            {2, 4, 6},
            {2, 5, 4},
            {3, 5, 2},
            {4, 5, 6}
    };
    kruskal(e, sizeof(e) / sizeof(e[0]));
    return 0;
}