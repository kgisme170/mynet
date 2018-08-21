#include <iostream>
using namespace std;
/*功能:
 *设计二叉树算法，计算
 * 1 所有节点数 Done
 * 2 叶子节点数 Done
 * 3 转换成链表 Done
 * 4 为每个节点增加双亲节点 在createBst的过程中，每次传入双亲节点信息即可 Done
 * 5 求值为x的层次号 Done
 * 6 已知先序和中序遍历，构造树 Done
 * 7 用栈来遍历 Done
 */
struct node{
    int value;
    node* lchild;
    node* rchild;
    node(int v):value(v),lchild(NULL),rchild(NULL){}
    node(const node& n):value(n.value),lchild(n.lchild),rchild(n.rchild){}
};
class bt{
    size_t allElements;
    size_t leafElements;
    node* root;
    void del(node* p){
        if(!p)return;
        if(p->lchild)del(p->lchild);
        if(p->rchild)del(p->rchild);
        delete p;
    }
    void preorder(node* p){
        if(!p)return;
        cout<<p->value<<',';
        ++allElements;
        if(p->lchild){
            preorder(p->lchild);
        }if(p->rchild){
            preorder(p->rchild);
        }
        if(!p->lchild && !p->rchild){
            ++leafElements;
        }
    }
    void inorder(node* p){
        if(!p)return;
        ++allElements;
        if(p->lchild){
            inorder(p->lchild);
        }
        cout<<p->value<<',';
        if(p->rchild){
            inorder(p->rchild);
        }
        if(!p->lchild && !p->rchild){
            ++leafElements;
        }
    }
    node* createBst(int pre[], int in[],
                    size_t l1/*pre范围左界*/,
                    size_t r1/*pre范围右界*/,
                    size_t l2/*in范围左界*/,
                    size_t r2/*in范围右界*/){
        if(l1>r1)return NULL;//要处理的pre子树长度=0，返回空指针
        size_t i;
        for(i=l2;i<r2;++i){
            if(in[i]==pre[l1])break;
        }//左子树的元素个数是i-l2-1个, 因此新的r1是l1+1+(i-l2-1)=l1+i-l2
        node* n = new node(pre[l1]);
        //cout<<"n="<<n<<",value="<<n->value<<'\n';
        n->lchild = createBst(pre, in, l1+1, l1+i-l2, l2, i-1);//pre和in分别取左子树
        n->rchild = createBst(pre, in, l1+i-l2+1, r1, i+1, l2);//pre和in分别取右子树
        return n;
    }
    void link(node* p, node*& head, node*& tail){
        //cout<<"link(,,)\n";
        if(p==NULL)return;
        //对于叶子节点，修改右指针==对于每个新来的叶子节点，用tail->next指向它，并更新tail
        if(p->lchild==NULL && p->rchild==NULL){
            if(head == NULL){ // 初始化
                head = p;
                tail = p;
                //cout<<"初始化:"<<p->value<<'\n';
            }else{
                tail->lchild = NULL;
                tail->rchild = p;
                tail = p;
                //cout<<"tail插入:"<<p->value<<'\n';
            }
        }
        link(p->lchild, head, tail);
        if(p->lchild){
            tail->lchild = NULL;
            tail->rchild = p;
            tail = p;
            //cout<<"tail插入:"<<p->value<<'\n';
        }
        else if(p->rchild){
            tail->lchild = NULL;
            tail->rchild = p;
            tail = p;
            //cout<<"tail插入:"<<p->value<<'\n';
        }
        link(p->rchild, head, tail);
    }
    bool find(node* p, int v){
        if(p==NULL)return false;
        if(p->value == v)return true;
        bool lFind = find(p->lchild, v);
        bool rFind = find(p->rchild, v);
        return lFind || rFind;
    }
    bool find(node* p, int v, size_t& level){
        if(p==NULL)return false;
        if(p->value == v)return true;
        if(find(p->lchild, v, ++level))return true;
        if(find(p->rchild, v, ++level))return true;
        return false;
    }
public:
    bt():root(NULL){}
    ~bt(){del(root);}
    bt(int pre[], int in[], size_t preSize, size_t inSize){//已知先序和中序遍历，构造树
        root = createBst(pre, in, 0, preSize-1, 0, inSize-1);
    }
    void printPreorder(){
        cout<<"先序";
        allElements = 0;
        leafElements = 0;
        preorder(root);
        cout<<'\n';
        cout<<"全部结点="<<allElements<<",叶子结点="<<leafElements<<'\n';
    }
    void printInorder(){
        cout<<"中序";
        allElements = 0;
        leafElements = 0;
        inorder(root);
        cout<<'\n';
        cout<<"全部结点="<<allElements<<",叶子结点="<<leafElements<<'\n';
    }
    void link(){
        //中序遍历并输出所有的节点
        cout<<"转化为链表\n";
        if(!root)
            return;
        node *head = NULL, *tail = NULL;
        link(root, head, tail);
        node* p = head;
        while(p){
            cout<<p->value<<',';
            p = p->rchild;
        }
        cout<<'\n';
    }
    bool find(int val){return find(root, val);}
    bool find(int val, size_t& level){
        level = 1;
        return find(root, val, level);
    }
    void stackPreOrder(){
        cout<<"堆栈遍历开始\n";
        if(!root)return;
        node* stack[1024];
        int idx = -1;
        #define PUSH(p) {stack[++idx] = p; /*cout<<"PUSH:"<<p->value<<'\n';*/}
        #define POP {--idx; /*cout<<"POP\n";*/}
        PUSH(root); // 初始化，根节点入栈
        while(idx>=0){
            node* p = stack[idx];
            cout<<p->value<<',';
            POP
            if(p->rchild){
                PUSH(p->rchild);
            }
            if(p->lchild){
                PUSH(p->lchild);
            }
        }
        cout<<"\n堆栈遍历结束\n";
    }
};
int main(){
    int pre[]={1,2,4,5,3,6};
    int in []={4,2,5,1,3,6};
    bt b(pre, in, sizeof(pre)/sizeof(int), sizeof(in)/sizeof(int));
    b.printPreorder();
    b.printInorder();
    b.stackPreOrder();

    cout<<boolalpha<<"4是否找到="<<b.find(4)<<",7是否找到="<<b.find(7)<<'\n';
    size_t level;
    b.find(2,level);
    cout<<"元素2在第"<<level<<"层\n";
    b.find(6,level);
    cout<<"元素6在第"<<level<<"层\n";
    b.link();//已经破坏了
    return 0;
}
