#include <algorithm>
#include <iostream>
#include <list>
using namespace std;
/*功能: 设计LRU算法
 * 从待排序的集合构造初始归并集合
 * 构造败者树, 败者树一共n个节点，构造n个叶子节点后才算完成，n个非叶子节点会有n+1个叶子节点
 * 败者树构造和调整的算法
 */
struct LUR1 {//缺点：容易造成缓存污染
    size_t bufSize;
    list<int> buf;

    LUR1(size_t s) : bufSize(s) {}

    //返回值是否命中缓存
    bool update(const int data) {//新来一个数据项，检查是否放入缓存
        list<int>::iterator it = find(buf.begin(), buf.end(), data);
        if (it != buf.end()) {//找到了，更新到头部
            if (it != buf.begin()) {
                buf.erase(it);
                buf.push_front(data);
            }//头部元素不需要移动
            return true;
        } else {//没有找到
            if (buf.size() < bufSize) {//队列不满
                buf.push_front(data);
                return false;
            } else {//队列已满
                buf.pop_back();
                buf.push_front(data);
            }
            return false;
        }
    }

    void update(int *data, size_t len) {
        for (size_t i = 0; i < len; ++i) {
            update(data[i]);
        }
    }

    void print() {
        for (auto i : buf) {
            cout << i << ',';
        }
        cout << '\n';
    }
};

struct TwoQ {
    size_t bufSize;
    list<int> fifo;
    LUR1 u;

    TwoQ(size_t s) : bufSize(s), u(s) {}

    void update(const int data) {
        //先在fifo里面找，如果存在，则放入u
        list<int>::iterator it = find(fifo.begin(), fifo.end(), data);
        if (it != fifo.end()) {//找到了
            u.update(data);
            fifo.erase(it);
        } else {//没有找到
            fifo.push_front(data);
            if (fifo.size() > bufSize) {
                fifo.pop_back();
            }
        }
    }

    void update(int *data, size_t len) {
        for (size_t i = 0; i < len; ++i) {
            update(data[i]);
        }
    }

    void print() {
        u.print();
    }
};
int main() {
    int data[] = {1, 2, 3, 4, 2, 1, 1, 3, 5, 5};
    LUR1 u1(4);
    u1.update(data, sizeof(data) / sizeof(data[0]));
    u1.print();

    TwoQ q(4);
    q.update(data, sizeof(data) / sizeof(data[0]));
    q.print();
    return 0;
}