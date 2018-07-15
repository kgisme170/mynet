#include <cstdlib>
#include <iostream>
#include <vector>
using namespace std;
/*功能: 设计置换-选择排序
 * 从待排序的集合构造初始归并集合
 * 构造败者树, 败者树一共n个节点，构造n个叶子节点后才算完成，n个非叶子节点会有n+1个叶子节点
 * 败者树构造和调整的算法
 */

const size_t testLen = 5;
struct testWorker{//败者树的数据输入输出
    size_t length(){return testLen;}
    void input(int& k){
        const static int buf[testLen] = {10, 9, 20, 6, 12};
        static size_t idx = 0;
        if(idx<testLen){
            k = buf[idx++];
        }else{
            cout<<"idx越界="<<idx<<'\n';
            if(idx == testLen){
                ++idx;
                k = 15;
            }else{
                k = INT_MAX;
            }
        }
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
        cout<<"->Adjust(s)="<<s<<'\n';
        size_t t = (s+k)/2; // t是双亲节点
        while(t>0){
            cout<<"t="<<t<<',';
            if(b[s]>b[ls[t]]){//ls[t]指向败者, s用于比较，指向胜者
                cout<<"交换b["<<s<<"]("<<b[s]<<")和ls["<<t<<"]("<<ls[t]<<"),";
                size_t tmpS = s;
                s = ls[t];
                ls[t] = tmpS;
            }
            t/=2;
            cout<<'\n';
        }
        ls[0]=s;
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
            IoWorker::input(b[i]);
        }
        //CreateLoserTree() 创建败者树
        for(int i=k-1;i>=0;--i){
            Adjust(i);//从后往前调整所有叶子结点到根的路径
            printLoserTree();
        }
        //CreateLoserTree() ends
        //while(b[ls[0]]!=INT_MAX){
            int q = ls[0];//ls[0];
            IoWorker::output(q);
            IoWorker::input(b[q]);
            Adjust(q);
        //}
    }
    void printLoserTree(){
        for(size_t i=0;i<k;++i){
            cout<<ls[i]<<',';
        }
        cout<<'\n';
    }
};
int main(){
    K_Merge<int, testWorker> mTest;
    mTest.merge();
    mTest.printLoserTree();
    return 0;
}
