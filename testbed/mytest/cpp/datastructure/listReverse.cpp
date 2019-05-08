#include <iostream>
using namespace std;
/*功能:
 *有头节点的顺序表逆置
 */
struct node {
    int value;
    node *next;
};
struct list {
    node *root;

    list(int *buf, size_t size) {
        root = new node();
        node *p = root;
        for (size_t i = 0; i < size; ++i) {
            node *n = new node;
            n->value = buf[i];
            n->next = NULL;
            p->next = n;
            p = p->next;
        }
    }

    ~list() {
        node *p = root;
        while (p) {
            node *tmp = p;
            p = p->next;
            delete tmp;
        }
    }

    void reverse() {
        node *p = root->next; // 单链表的头插入方法 p(2)
        root->next = NULL;
        while (p) {//从第一个元素开始
            node *q = p->next;
            p->next = root->next;
            root->next = p;
            p = q;
        }
    }

    void print() {
        cout << "\n<=======\n";
        node *p = root->next;
        while (p) {
            cout << p->value << ',';
            p = p->next;
        }
        cout << "\n=======>\n";
    }
};
int main() {
    int buf[] = {2, -1, 7, 1, 6, 4, 5};
    list l(buf, sizeof(buf) / sizeof(int));
    l.print();
    l.reverse();
    l.print();
    return 0;
}