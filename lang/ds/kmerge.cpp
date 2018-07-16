#include <algorithm>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <limits.h>
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
    virtual size_t dataDimemsion() = 0;
    virtual size_t dataNumber() = 0;
    virtual int getNext(size_t arrayIdx) = 0;
    virtual ~dataSource(){}
};

struct IoWorker{
    virtual size_t dataDimemsion() = 0;
    virtual size_t dataNumber() = 0;
    virtual int input(size_t arrayIdx) = 0;
    virtual void output(int i) = 0;
    virtual ~IoWorker(){}
    virtual size_t totalCount() = 0;
};
struct dataWorker : IoWorker{//败者树的数据输入输出
    dataSource* source;
    bool shouldDelete;
    size_t count;
public:
    dataWorker(dataSource* p, bool d = true):
        source(p),
        shouldDelete(d),
        count(0){}
    ~dataWorker(){
        if(shouldDelete){
            delete source;
        }
    }
    size_t dataDimemsion(){return source->dataDimemsion();}
    size_t dataNumber(){return source->dataNumber();}
    int input(size_t arrayIdx){
        if(arrayIdx>=dataDimemsion()){
            cerr<<"编程错误, arrayIdx="<<arrayIdx<<'\n';
            exit(1);
        }
        return source->getNext(arrayIdx);
    }
    void output(int i){
        ++count;
        cout<<i<<',';
    }
    size_t totalCount(){return count;}
};

//k-路 归并
class K_Merge{
protected:
    IoWorker* worker;
    bool shouldDelete;
    vector<size_t> ls;//LoserTree结点，存储下标
    vector<int> b;//External结点
    size_t k;//归并段的数量

    void Adjust(size_t s){
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
        ls[0]=s;
        printLoserTree();
    }
public:
    K_Merge(IoWorker* p, bool d/*是否管理pWorker的生命周期*/ = true):
        worker(p),
        shouldDelete(d)
    {
        COUT<<"K_Merge ctor begin\n";
        k = worker->dataDimemsion();
        ls.resize(k);
        b.resize(k+1);
        b[k]=INT_MIN;//用于初始化第一次Adjust
        for(size_t i=0;i<k;++i){
            ls[i]=k;//败者树的初始值，指向b[k]
        }
        COUT<<"K_Merge ctor end\n";
    }
    virtual ~K_Merge(){
        if(shouldDelete){
            delete worker;
        }
    }
    virtual void merge(){
        for(size_t i=0;i<k;++i){
            b[i] = worker->input(i);
        }
        //CreateLoserTree() 创建败者树
        cout<<"CreateLoserTree() begins======================\n";
        for(int i=k-1;i>=0;--i){
            Adjust(i);//从后往前调整所有叶子结点到根的路径
        }
        cout<<"CreateLoserTree() ends========================\n";
        size_t len = worker->dataNumber();
        size_t outputNum = 0;
        while(outputNum<len){//(b[ls[1]]!=INT_MAX){
            int q = ls[0];//ls[0];
            COUT<<"输出位置="<<q<<",值=";
            worker->output(b[q]);
            b[q] = worker->input(q);
            COUT<<"补充的新值="<<b[q]<<'\n';
            Adjust(q);
            ++outputNum;
        }
        cout<<"总共输出"<<worker->totalCount()<<"个值\n";
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
    size_t dataDimemsion(){return numData;}
    vector<vector<int>> getData(){return initVector;}
    vector<vector<int>> getSortedData(){ //简单排序
        for(size_t i=0;i<initVector.size();++i){
            sort(initVector[i].begin(),initVector[i].end());
        }
        return initVector;
    }
};

struct vectorDataSource:dataSource{//2维
    vector<vector<int>> dataVector;
    vector<size_t> bufIndexVector;
public:
    vectorDataSource(const vector<vector<int>>& v):
        dataVector(v),
        bufIndexVector(v.size())
    {}
    size_t dataDimemsion(){return dataVector.size();}
    size_t dataNumber(){
        size_t sum = 0;
        for(size_t i=0;i<dataVector.size();++i){
            sum += dataVector[i].size();
        }
        return sum;
    }
    int getNext(size_t arrayIdx){
        COUT<<"getNext("<<arrayIdx<<") begin\n";
        size_t& idx = bufIndexVector[arrayIdx];
        int r = (idx>dataVector[arrayIdx].size()-1)
            ? INT_MAX : dataVector[arrayIdx][idx];
        if(idx>dataVector[arrayIdx].size()){
            cerr<<"arrayIdx="<<arrayIdx<<"到达最大:"<<idx<<"===========================\n";
            exit(1);
        }
        ++idx;
        COUT<<"getNext("<<arrayIdx<<") end\n";
        return r;
    }
};
void f1()
{
    cout<<"测试1\n";
    vector<vector<int>> v;
    v.push_back({10,15,16});
    v.push_back({9,18,21});
    v.push_back({20,22,40});
    v.push_back({6,17,25});
    v.push_back({12,37,48});
    K_Merge mArray(new dataWorker(new vectorDataSource(v)));
    mArray.merge();
    mArray.printLoserTree();
}
const size_t cacheSize2 = 6;
const static int testData2[] = {51, 49, 39, 46, 38, 29, 14, 61, 15, 30, 1,  48, 52, 3,  63, 27, 4,  13, 89, 24, 46, 58, 33, 76};
void f2()
{
    cout<<"测试2\n";
    vectorData vd(testData2,
                 sizeof(testData2)/sizeof(testData2[0]),
                 cacheSize2);
    K_Merge mVector(
        new dataWorker(
            new vectorDataSource(vd.getSortedData())));
    mVector.merge();
    mVector.printLoserTree();
}

struct arrayDataSource:dataSource{//1维，用于构造初始归并集合
    const int* data;
    size_t dataSize;
    size_t cacheLen;
    size_t idx;
public:
    arrayDataSource(const int* d, size_t s, size_t l):data(d),dataSize(s),cacheLen(l),idx(0){}
    size_t dataDimemsion(){return cacheLen;}
    size_t dataNumber(){return dataSize;}
    int getNext(size_t arrayIdx){
        COUT<<"getNext("<<arrayIdx<<"), idx="<<idx<<",data="<<data[idx]<<"\n";
        if(idx==dataSize)return INT_MAX;
        if(idx>=dataSize){
            cerr<<"arrayDataSource编程错误\n";
            exit(1);
        }
        return data[idx++];
    }
};
struct arrayDataWorker : dataWorker{//败者树的数据输入输出
    arrayDataWorker(dataSource* p, bool d = true):
        dataWorker(p, d){COUT<<"arrayDataWorker ctor\n";}
    int input(size_t arrayIdx){
        return source->getNext(arrayIdx);
    }
    void output(int i){
        ++count;
        cout<<i<<',';
    }
};
void f3(){
    cout<<"测试3\n";
    K_Merge mAlgo(
        new arrayDataWorker(
            new arrayDataSource(testData2, sizeof(testData2)/sizeof(int), cacheSize2)));
    mAlgo.merge();
    mAlgo.printLoserTree();
}

class K_Merge_unsorted: public K_Merge{
    //int miniMax;
    int firstUpdate = INT_MIN;
    bool Adjust(size_t s, int){
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
        if(b[s]>firstUpdate){
            COUT<<"\n更新b[s]="<<b[s]<<",firstUpdate="<<firstUpdate<<"\n";
            ls[0]=s;
            firstUpdate = b[s];
            //printLoserTree();
            return true;
        }else{
            COUT<<"\n不更新b[s]="<<b[s]<<"\n";
            return false;
        }
    }
    vector<int> result;
public:
    K_Merge_unsorted(IoWorker* p, bool d/*是否管理pWorker的生命周期*/ = true):
        K_Merge(p,d)//,
        //miniMax(INT_MIN)
    {}
    void merge(){
        for(size_t i=0;i<k;++i){
            b[i] = worker->input(i);
        }
        //CreateLoserTree() 创建败者树
        cout<<"CreateLoserTree() begins======================\n";
        for(int i=k-1;i>=0;--i){
            K_Merge::Adjust(i);//从后往前调整所有叶子结点到根的路径
        }
        cout<<"CreateLoserTree() ends========================\n";
        size_t len = worker->dataNumber();
        size_t outputNum = 0;
        static int bFirstUpdate = true;
        size_t dim = worker->dataDimemsion();
        size_t failedCount = 0;
        while(outputNum<len){//(b[ls[1]]!=INT_MAX){
            int q = ls[0];//ls[0];
            COUT<<"输出位置="<<q<<",值=";
            worker->output(b[q]);
            if(bFirstUpdate){
                firstUpdate = b[q];
                bFirstUpdate = false;
            }
            b[q] = worker->input(q);
            COUT<<"补充的新值="<<b[q]<<'\n';
            if(Adjust(q, 0)){
                int champion = b[ls[0]];
                if(champion != INT_MAX){
                    COUT<<"输出="<<champion<<'\n';
                    result.push_back(champion);
                }
            }else{
                ++failedCount;
                if(failedCount == dim){
                    break;
                }
            }
            ++outputNum;
        }
        //cout<<"总共输出"<<worker->totalCount()<<"个值\n";
        cout<<'\n';
    }
    vector<int> getResult(){return result;}
};

int main(){
    //f1();    f2();    f3();    return 0;
    cout<<"测试4\n";//下面的功能未完成
    K_Merge_unsorted mAlgo(
        new arrayDataWorker(
            new arrayDataSource(testData2, sizeof(testData2)/sizeof(int), cacheSize2)));
    mAlgo.merge();
    vector<int> result = mAlgo.getResult();
    cout<<"=======\n";
    for(size_t i=0;i<result.size();++i){
        cout<<result[i]<<'\n';
    }
    return 0;
}
