#include <iostream>
#include <deque>
#include <limits>
using namespace std;
const int INT_MAX = numeric_limits<std::int32_t>::max();
const int INT_MIN = numeric_limits<std::int32_t>::min();
/*功能: 广度优先构造并遍历
 */
class testLeaf{
    size_t len;//树的叶子节点值
public:
    testLeaf(size_t l):len(l){}
    size_t length(){return len;}
    int next(){return 0;}
    bool hasNext(){return false;}
};
template<typename T>
struct node{//叶子节点指向真正的数据区域
    int value;
    node* lchild;
    node* rchild;
    node* parent;//方便败者树的调整
    T* leaf;//不负责创建, 但负责销毁
    node(node* p):value(INT_MIN),lchild(NULL),rchild(NULL),parent(p),leaf(NULL){cout<<"node ctor\n";}
    node(node* p, T* l):value(l->length()),lchild(NULL),rchild(NULL),parent(p),leaf(l){cout<<"node ctor\n";}
    ~node(){delete leaf;}
};
template<typename T>
class bt{
    node<T> winner;
    node<T>* looser;
    void del(node<T>* p){
        if(!p)return;
        if(p->lchild)del(p->lchild);
        if(p->rchild)del(p->rchild);
        delete p;
    }
    void createBt(size_t len){//len总是>0 广度优先构造树
        looser = new node<T>(&winner);
        deque<node<T>*> q;
        q.push_back(looser);
        size_t count = 1;
        while(count < len-1){// 非叶子节点的数量=len-1
            node<T>* n = q.front();
            cout<<"front()\n";
            n->lchild = new node<T>(n);
            q.push_back(n->lchild);
            cout<<"push_back left\n";
            ++count;
            if(count < len-1){
                n->rchild = new node<T>(n);
                q.push_back(n->rchild);
                cout<<"push_back right\n";
                ++count;
            }
            cout<<"pop_front()\n";
            q.pop_front();
        }
        cout<<"共"<<count<<"个非叶子节点\n";
    }
    void preorderAttach(T* p[], node<T>* n, size_t& idx){
        if(!n)return;
        if(n->lchild){
            preorderAttach(p, n->lchild, idx);
        }
        else{
            n->lchild = new node<T>(n, p[idx++]);
        }

        if(n->rchild){
            preorderAttach(p, n->rchild, idx);
        }
        else{
            n->rchild = new node<T>(n, p[idx++]);
        }
    }
    void printPreOrder(node<T>* p){
        if(!p)return;
        if(p->leaf){
            cout<<p->leaf->length()<<',';
        }
        if(p->lchild){
            printPreOrder(p->lchild);
        }if(p->rchild){
            printPreOrder(p->rchild);
        }
    }
public:
    ~bt(){
        del(looser);
    }
    bt(T* p[], size_t len):winner(NULL){//构造
        if(len < 2){
            cout << "败者树的初始归并集合数量不应该小于2\n";
            return;
        }
        createBt(len);
        size_t idx = 0;
        preorderAttach(p, looser, idx);
    }
    void printPreorder(){
        cout<<"先序";
        printPreOrder(looser);
        cout<<'\n';
    }
};
int main(){
    testLeaf* values[] = {
        new testLeaf(6),
        new testLeaf(12),
        new testLeaf(10),
        new testLeaf(9),
        new testLeaf(20),
    };

    bt<testLeaf> b(values, sizeof(values)/sizeof(values[0]));
    b.printPreorder();
    return 0;
}
