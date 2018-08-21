#include <iostream>
using namespace std;
/*功能:
 *将单链表分成两个，元素分别是奇数和偶数
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
        cout<<"==========\n";
        node* cur = first;
        while(cur){
            cout << cur->value << '\n';
            cur = cur->next;
        }
        cout<<"==========\n";
    }
    void split(singleList &odd, singleList& even){//删除最小节点
        if(first == NULL)return;
        node** pOdd = &odd.first;
        node** pEven = &even.first;
        node* cur = first;
        while(cur){
            cout<<cur->value<<' ';
            if(cur->value%2==0){
                *pEven = cur;
                pEven = &cur->next;
                cout<<"even\n";
            }else{
                *pOdd = cur;
                pOdd = &cur->next;
                cout<<"odd\n";
            }
            cur = cur->next;
        }
        first = NULL;
        *pOdd = NULL;
        *pEven = NULL;
    }
};
int main()
{
    int buf[]={2,-1,7,1,4,4,5};
    singleList s(buf, sizeof(buf)/sizeof(buf[0]));
    singleList odd,even;
    s.split(odd,even);
    odd.print();
    even.print();
    return 0;
}
