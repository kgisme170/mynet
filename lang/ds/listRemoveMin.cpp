#include <iostream>
using namespace std;
/*功能:
 *有一个递增非空单链表，设计一个算法删除值域重复的结点
 */
struct node{
    int value;
    node* next;
    node():value(0),next(NULL){/*cout<<"ctor\n";*/}
    node(int v):value(v),next(NULL){}
    ~node(){/*cout<<"dtor\n";*/}
};
struct singleList{
    node* first;
    singleList():first(NULL){}
    ~singleList(){
        node* cur = first;
        while(cur){
            node* pNext = cur->next;
            delete cur;
            cur = pNext;
        }
    }
    singleList(int* buf, size_t size){
        //单链表有首节点的时候构造比较容易
        //没有首节点的时候写while循环比较容易
        if(size == 0)return;
        first = new node(buf[0]);
        node* p = first;
        for(size_t i = 1; i < size; ++i){
            p->next = new node(buf[i]);
            p = p->next;
        }
        //cout<<"singleList ctor done\n";
    }
    void print(){
        node* cur = first;
        while(cur){
            cout << cur->value << '\n';
            cur = cur->next;
        }
        cout<<"==========\n";
    }
    void removeMin(){//删除最小节点
        if(first == NULL)return;
        node* cur = first;
        node* pMin = cur;
        node* pre = cur;
        node* preMin = cur;
        int value = cur->value;
        while(cur){
            if(value>cur->value){
                value = cur->value;
                pMin = cur;
                preMin = pre;
            }
            pre = cur;
            cur = cur->next;
        }//已经找到了pMin
        if(pMin==first){
            first = first->next;
            delete first;
        }else{
            preMin->next = pMin->next;
            delete pMin;
        }
    }
};
int main()
{
    int buf[]={2,-1,7,0,1,2};
    singleList s(buf, sizeof(buf)/sizeof(buf[0]));
    s.print();
    s.removeMin();
    s.print();
    return 0;
}
