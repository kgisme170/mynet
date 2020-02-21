#include <atomic>
using namespace std;
template<typename _Ty>
struct TLockFreeStack {
    struct Node {
        _Ty val;
        Node* next;
    };
    TLockFreeStack() : head(nullptr) {}
    void push(const _Ty& val) {
        Node* node = new Node{ val, head.load() };
        while (!head.compare_exchange_strong(node->next, node)) {
        }
    }
    void pop() {
        auto node = head.load();
        while (node && !head.compare_exchange_strong(node, node->next)) {
        }
        if (node) delete node;
    }
    std::atomic<Node*> head;
};
int main() {
    return 0;
}
