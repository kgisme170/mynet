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
const size_t testLen = 5;
const static int buf[testLen][3] = {
    {10,15,16},
    {9,18,21},
    {20,22,40},
    {6,17,25},
    {12,37,48}
};
size_t bufIndex[testLen] = {0};
struct testWorker{//败者树的数据输入输出
    size_t length(){return testLen;}
    void input(int& k, size_t arrayIdx){
        if(arrayIdx>=testLen){
            cout<<"编程错误, arrayIdx="<<arrayIdx<<'\n';
            exit(1);
        }
        size_t& idx = bufIndex[arrayIdx];
        //cout<<"idx="<<idx<<",size="<<sizeof(buf[0])/sizeof(int)<<'\n';
        //if(idx==sizeof(buf[0])/sizeof(int))cout<<"到达结尾\n";
        k = (idx>=sizeof(buf[0])/sizeof(int)) ? INT_MAX : buf[arrayIdx][idx];
        ++idx;
    }
    void output(int i){cout<<"i="<<i<<'\n';}
    void init(){}//初始化归并段
};
const size_t dataBufSize = 6;
const static int data[] = {
    51, 49, 39, 46, 38,
    29, 14, 61, 15, 30,
    1,  48, 52, 3,  63,
    27, 4,  13, 89, 24,
    46, 58, 33, 76
};
class mergedData{
    size_t bufSize;
    size_t numData;
    vector<vector<int>> initVector;
public:
    mergedData():bufSize(dataBufSize){
        numData = sizeof(data)/sizeof(data[0]);
        size_t numVector = numData / bufSize + numData % bufSize;//如果不整除就要加1
        cout<<"vector的数量="<<numVector<<'\n';
        initVector.resize(numVector);
        for(size_t i=0;i<numData;++i){
            size_t vIdx = i / bufSize;//第几个vector
            initVector[vIdx].push_back(data[i]);
        }
        for(size_t i=0;i<numVector;++i){
            cout<<"第"<<i<<"个vector的元素个数="<<initVector[i].size()<<',';
        }
        cout<<'\n';
    }
    size_t length(){return numData;}
    void input(int& k, size_t arrayIdx){
        if(arrayIdx>=testLen){
            cout<<"编程错误, arrayIdx="<<arrayIdx<<'\n';
            exit(1);
        }
        size_t& idx = bufIndex[arrayIdx];
        k = (idx>=testLen) ? INT_MAX : buf[arrayIdx][idx];
        ++idx;
    }
    void output(int i){}
    void init(){}//初始化归并段
};
//k-路 归并
template<typename KeyType, typename IoWorker>
class K_Merge: public IoWorker{
    vector<size_t> ls;//LoserTree结点，存储下标
    vector<KeyType> b;//External结点
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
    K_Merge(){
        IoWorker::init();
        k = IoWorker::length();
        ls.resize(k);
        b.resize(k+1);
        b[k]=INT_MIN;//用于初始化第一次Adjust
        for(size_t i=0;i<k;++i){
            ls[i]=k;//败者树的初始值，指向b[k]
        }
    }
    void merge(){
        for(size_t i=0;i<k;++i){
            IoWorker::input(b[i], i);
        }
        //CreateLoserTree() 创建败者树
        for(int i=k-1;i>=0;--i){
            Adjust(i);//从后往前调整所有叶子结点到根的路径
        }
        cout<<"CreateLoserTree() ends========================\n";
        while(b[ls[1]]!=INT_MAX){
            int q = ls[0];//ls[0];
            COUT<<"输出位置="<<q<<",值=";
            IoWorker::output(b[q]);
            IoWorker::input(b[q], q);
            COUT<<"补充的新值="<<b[q]<<'\n';
            Adjust(q);
        }
    }
    void printLoserTree(){
        for(size_t i=0;i<k;++i){
            COUT<<ls[i]<<',';
        }
        COUT<<'\n';
    }

    void drawLoserTree(){
        struct IntervalYield{
            size_t n;
            size_t count = 0;
            IntervalYield(size_t _n):n(_n){
                size_t height = 1;
                while(n){
                    ++height;
                    n/=2;
                }
                cout<<height<<'\n';
            }
            void drawNext(const string& msg){

            }
        }y(ls.size());
        // 计算树的层数
    }
};
int main(){
    K_Merge<int, testWorker> mTest;
    mTest.merge();
    mTest.printLoserTree();
    mTest.drawLoserTree();
    mergedData d;
    return 0;
}
