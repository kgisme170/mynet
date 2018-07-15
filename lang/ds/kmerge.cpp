#include <algorithm>
#include <cstdlib>
#include <iostream>
#include <vector>
using namespace std;
/*功能: 设计置换-选择排序
 * 从待排序的集合构造初始归并集合
 * 构造败者树, 败者树一共n个节点，构造n个叶子节点后才算完成，n个非叶子节点会有n+1个叶子节点
 * 败者树构造和调整的算法
 */
struct DevNull:ostream{}dev;
//#define DEBUG
#ifdef DEBUG
#define COUT cout
#else
#define COUT dev
#endif // define
struct dataSource{
    virtual size_t length() = 0;
    virtual int getNext(size_t arrayIdx) = 0;
    virtual ~dataSource(){}
};

struct IoWorker{
    virtual size_t length() = 0;
    virtual int input(size_t arrayIdx) = 0;
    virtual void output(int i) = 0;
    virtual ~IoWorker(){}
};
struct dataWorker : IoWorker{//败者树的数据输入输出
    dataSource* source;
    bool shouldDelete;
public:
    dataWorker(dataSource* p, bool d = true):
        source(p),
        shouldDelete(d){}
    ~dataWorker(){
        if(shouldDelete){
            delete source;
        }
    }
    size_t length(){return source->length();}
    int input(size_t arrayIdx){
        if(arrayIdx>=length()){
            cerr<<"编程错误, arrayIdx="<<arrayIdx<<'\n';
            exit(1);
        }
        return source->getNext(arrayIdx);
    }
    void output(int i){cout<<i<<',';}
};

//k-路 归并
class K_Merge{
    IoWorker* worker;
    bool shouldDelete;
    bool bMiniMax;
    int miniMax;
    vector<size_t> ls;//LoserTree结点，存储下标
    vector<int> b;//External结点
    size_t k;//归并段的数量

    bool Adjust(size_t s){
        COUT<<"->Adjust(s)="<<s<<'\n';
        size_t t = (s+k)/2; // t是双亲节点
        while(t>0){
            COUT<<"t="<<t<<',';
            if(b[s]>b[ls[t]]){//ls[t]指向败者, s用于比较，指向胜者
                COUT<<"交换b["<<s<<"]("<<b[s]<<")和ls["<<t<<"]("<<ls[t]<<"),";
                size_t tmpS = s;
                s = ls[t];
                COUT<<"s的新值="<<s<<'\n';
                ls[t] = tmpS;
            }
            t/=2;
            COUT<<'\n';
        }
        if(!bMiniMax/*普通流程*/ || ls[s]>=INT_MIN/*可以更新*/){
            ls[0]=s;
            printLoserTree();
            return true;
        }else{
            return false;
        }
    }
public:
    K_Merge(IoWorker* p,
            bool d = true, /*是否管理pWorker的声明周期*/
            bool m = false/*是否采用置换-选择排序的miniMax判断*/):
        worker(p),
        shouldDelete(d),
        bMiniMax(m),
        miniMax(INT_MIN)
    {
        k = worker->length();
        ls.resize(k);
        b.resize(k+1);
        b[k]=INT_MIN;//用于初始化第一次Adjust
        for(size_t i=0;i<k;++i){
            ls[i]=k;//败者树的初始值，指向b[k]
        }
    }
    ~K_Merge(){
        if(shouldDelete){
            delete worker;
        }
    }
    void merge(){
        for(size_t i=0;i<k;++i){
            b[i] = worker->input(i);
        }
        //CreateLoserTree() 创建败者树
        for(int i=k-1;i>=0;--i){
            Adjust(i);//从后往前调整所有叶子结点到根的路径
        }
        cout<<"CreateLoserTree() ends========================\n";
        while(b[ls[1]]!=INT_MAX){
            int q = ls[0];//ls[0];
            COUT<<"输出位置="<<q<<",值=";
            worker->output(b[q]);
            b[q] = worker->input(q);
            COUT<<"补充的新值="<<b[q]<<'\n';
            if(!Adjust(q)){
                break;
            }
        }
        cout<<'\n';
    }
    void printLoserTree(){
        for(size_t i=0;i<k;++i){
            COUT<<ls[i]<<',';
        }
        COUT<<'\n';
    }
};

class vectorData{
    size_t numData;
    size_t bufSize;
    vector<vector<int>> initVector;
public:
    vectorData(const int* data, size_t n, size_t s):
            numData(n), bufSize(s){
        size_t numVector = numData / bufSize + numData % bufSize;//如果不整除就要加1
        COUT<<"vector的数量="<<numVector<<'\n';
        initVector.resize(numVector);
        for(size_t i=0;i<numData;++i){
            size_t vIdx = i / bufSize;//第几个vector
            initVector[vIdx].push_back(data[i]);
        }
        for(size_t i=0;i<numVector;++i){
            COUT<<"第"<<i<<"个vector的元素个数="<<initVector[i].size()<<',';
        }
        COUT<<'\n';
    }
    vectorData(const vector<vector<int>>& v, size_t b):
        numData(0), bufSize(b), initVector(v){
        for(size_t i=0;i<v.size();++i){
            numData += v[i].size();
        }
    }
    size_t length(){return numData;}
    vector<vector<int>> getData(){return initVector;}
    vector<vector<int>> getSortedData(){ //简单排序
        for(size_t i=0;i<initVector.size();++i){
            sort(initVector[i].begin(),initVector[i].end());
        }
        return initVector;
    }
};

struct vectorDataSource:dataSource{
    vector<vector<int>> dataVector;
    vector<size_t> bufIndexVector;
public:
    vectorDataSource(const vector<vector<int>>& v):
        dataVector(v),
        bufIndexVector(v.size())
    {}
    size_t length(){return dataVector.size();}
    int getNext(size_t arrayIdx){
        COUT<<"getNext("<<arrayIdx<<") begin\n";
        size_t& idx = bufIndexVector[arrayIdx];
        int r = (idx>=dataVector[idx].size())
            ? INT_MAX : dataVector[arrayIdx][idx];
        ++idx;
        COUT<<"getNext("<<arrayIdx<<") end\n";
        return r;
    }
};

int main(){
    vector<vector<int>> v;
    v.push_back({10,15,16});
    v.push_back({9,18,21});
    v.push_back({20,22,40});
    v.push_back({6,17,25});
    v.push_back({12,37,48});
    K_Merge mArray(new dataWorker(new vectorDataSource(v)));
    mArray.merge();
    mArray.printLoserTree();

    const size_t cacheSize2 = 6;
    const static int testData2[] = {
        51, 49, 39, 46, 38,
        29, 14, 61, 15, 30,
        1,  48, 52, 3,  63,
        27, 4,  13, 89, 24,
        46, 58, 33, 76
    };
    vectorData vd(testData2,
                 sizeof(testData2)/sizeof(testData2[0]),
                 cacheSize2);
    vectorData vd2 = vd;//没有std::sort()排序过的
    K_Merge mVector(
        new dataWorker(
            new vectorDataSource(vd.getSortedData())));
    mVector.merge();
    mVector.printLoserTree();
    return 0;
}
