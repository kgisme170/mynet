#include <iostream>
using namespace std;
/*功能:
 *逆序打印单链表的数据
 */
struct node {
    int value;
    node *next;

    node(int v) : value(v), next(NULL) {}
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
        if (size == 0)return;
        first = new node(buf[0]);
        node *p = first;
        for (size_t i = 1; i < size; ++i) {
            p->next = new node(buf[i]);
            p = p->next;
        }
        //cout<<"singleList ctor done\n";
    }

    void printReverse() {
        cout << "==========\n";
        _print(first);
    }

private:
    void _print(node *f) {
        if (f->next)_print(f->next);
        cout << f->value << ',';
    }
};
int main() {
    int buf[] = {2, -1, 7, 1, 4, 4, 5};
    singleList s(buf, sizeof(buf) / sizeof(buf[0]));
    s.printReverse();
    return 0;
}