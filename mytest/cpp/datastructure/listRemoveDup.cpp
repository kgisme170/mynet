#include <iostream>
using namespace std;
/*功能:
 *有一个递增非空单链表，设计一个算法删除值域重复的结点
 */
struct node {
    int value;
    node *next;

    node() : value(0), next(NULL) {/*cout<<"ctor\n";*/}

    node(int v) : value(v), next(NULL) {}

    ~node() {/*cout<<"dtor\n";*/}
};
struct singleList {
    node *first;

    singleList() : first(NULL) {}

    ~singleList() {
        node *cur = first;
        while (cur) {
            node *pNext = cur->next;
            delete cur;
            cur = pNext;
        }
    }

    singleList(int *buf, size_t size) {
        //单链表有首节点的时候构造比较容易
        //没有首节点的时候写while循环比较容易
        if (size == 0)return;
        first = new node(buf[0]);
        node *p = first;
        for (size_t i = 1; i < size; ++i) {
            p->next = new node(buf[i]);
            p = p->next;
        }
        //cout<<"singleList ctor done\n";
    }

    void print() {
        node *cur = first;
        while (cur) {
            cout << cur->value << '\n';
            cur = cur->next;
        }
    }

    singleList uniq() {
        singleList newList;
        if (first == NULL)return newList;
        node *cur = first;
        newList.first = new node(first->value);
        node *curNew = newList.first;
        while (cur) {//遍历原有数组
            if (curNew) { // 有节点
                if (curNew->value == cur->value) {
                    // 什么也不做
                } else { // 新建节点
                    cout << "if else, curValue =" << cur->value << ", my value=" << curNew->value << '\n';
                    curNew->next = new node(cur->value); // 新链表
                    curNew = curNew->next;
                }
            } else { // curNew不存在
                cout << "else\n";
                curNew = new node(cur->value); // 新链表
                curNew = curNew->next;
            }
            cur = cur->next;
            //cout<<"next\n";
        }
        return newList;
    }
};
int main() {
    int buf[] = {-2, -1, 0, 0, 1, 6, 6};
    singleList s(buf, sizeof(buf) / sizeof(buf[0]));
    s.print();
    cout << "----------\n";
    singleList u = s.uniq();
    u.print();
    return 0;
}