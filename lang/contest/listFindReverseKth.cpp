#include <iostream>
using namespace std;
/*功能:
 *寻找单链表的倒数第K的元素
 */
struct node{
    int value;
    node* next;
    node(int v):value(v),next(NULL){}
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
        if(size == 0)return;
        first = new node(buf[0]);
        node* p = first;
        for(size_t i = 1; i < size; ++i){
            p->next = new node(buf[i]);
            p = p->next;
        }
    }
    node* findReverseKthElement(size_t k){
        node* p = first;
        size_t i=1;
        while(p && i<k){
            p = p->next;
            ++i;
        }
        if (i<k){
            cout<<"元素个数不够\n";
            return first;//元素个数不够
        }
        node* again=first;
        while(p->next){
            p = p->next;
            again = again->next;
        }
        return again;
    }
};
int main()
{
    int buf[]={2,-1,7,1,6,4,5};
    singleList s(buf, sizeof(buf)/sizeof(buf[0]));
    node* p = s.findReverseKthElement(3);
    cout << p->value << '\n';
    return 0;
}
