#include <iostream>
using namespace std;
/*功能: roundrobinQueue
 *用链表实现一个循环队列，实现两端插入和删除
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

    void pushFront(int val) {
        //只有一个元素的情况
        if (rear && rear->next == NULL) {
            push(val);
            return;
        }
        node *pnew = new node();
        pnew->value = val;

        if (rear) {//&&rear->next != NULL
            node *n = rear->next->next;
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
    q.pushFront(4);
    q.push(3);
    node *p1 = q.pop();
    cout << p1->value << endl;
    delete p1;
    node *p2 = q.pop();
    cout << p2->value << endl;
    delete p2;
    return 0;
}