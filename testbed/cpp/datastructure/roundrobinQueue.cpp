#include <iostream>
using namespace std;
/*功能: roundrobinQueue
 *用链表实现一个循环队列，实现插入和删除
 */
struct node {
    int value;
    node *next;

    node() : value(-1), next(NULL) {}
};
struct rrq {
    node *rear;

    rrq() : rear(NULL) {}

    ~rrq() {
        if (rear == NULL)return;
        node *p = rear->next;
        while (p != NULL && p != rear) {
            node *tmp = p;
            p = p->next;
            delete tmp;
        }
        delete rear;
    }

    void push(int val) {
        node *pnew = new node();
        pnew->value = val;

        if (rear) {
            node *n = rear->next;
            rear->next = pnew;
            pnew->next = n;
        } else {
            rear = pnew;
            rear->next = rear;
        }
    }

    node *pop() {
        if (NULL == rear) {
            cout << "队列空，不能pop\n";
            return NULL;
        } else {
            node *front = rear->next;
            if (front == rear) {
                rear = NULL;
            } else {
                rear->next = front->next;
            }
            return front;
        }
    }
};
int main() {
    rrq q;
    q.push(2);
    q.push(3);
    node *p = q.pop();
    cout << p->value << endl;
    delete p;
    return 0;
}