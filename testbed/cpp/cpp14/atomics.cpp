#include<atomic>
#include<iostream>
using namespace std;

template<typename T>
struct stack {
    struct node {
        T data;
        node *next;

        node(const T &d) : data(d), next(nullptr) {}
    };

    atomic<node *> head;

    void push(const T &data) {
        node *newNode = new node(data);
        newNode->next = head.load(memory_order_relaxed);
        while (!head.compare_exchange_weak(newNode->next, newNode,
                                           memory_order_release, memory_order_relaxed));
    }
};
struct Big {
    int i;
    int j;
    int k[100];
};
int main() {
    atomic<int> i;
    cout << i.load() << endl;
    i.store(20);
    cout << i.load() << endl;
    i.exchange(30);
    cout << i.load() << endl;

    stack<int> s;
    s.push(3);
    s.push(4);

    atomic <Big> ab;
    Big expect, value;
    ab.compare_exchange_weak(expect, value, memory_order_release, memory_order_relaxed);
    return 0;
}