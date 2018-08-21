#include <iostream>
using namespace std;
/*功能: 设计二叉树算法，线索化二叉树*/
enum class flag{
    NONE, // 没有遍历过
    LINK, // 孩子指针
    CLUE, // 线索化指针
};
struct node{
    int value;
    node* lchild;
    node* rchild;
    flag lFlag;
    flag rFlag;
    node(int v):value(v),lchild(NULL),rchild(NULL),lFlag(flag::NONE),rFlag(flag::NONE){}
};
class threadedBt{
    node* root;
    void del(node* p){
        if(!p)return;
        if(p->lchild && p->lFlag == flag::LINK)del(p->lchild);
        if(p->rchild && p->rFlag == flag::LINK)del(p->rchild);
        delete p;
    }
    void preorder(node* p, node*& pre){ // 这个pre要被不断修改
        if(!p)return;
        if(!p->lchild){
            p->lFlag = flag::CLUE;
            p->lchild = pre;
        }
        if(pre && !pre->rchild){
            pre->rFlag = flag::CLUE;
            pre->rchild = p;
        }
        //cout<<p->value<<", 前驱="<<p->lchild->value<<",后继="<<p->rchild->value<<'\n';
        pre = p;
        if(p->lFlag == flag::LINK){
            preorder(p->lchild, pre);
        }
        if(p->rFlag == flag::LINK){
            preorder(p->rchild, pre);
        }
    }
    void inorder(node* p, node*& pre){ // 这个pre要被不断修改
        if(!p)return;
        inorder(p->lchild, pre);
        if(!p->lchild){
            p->lFlag = flag::CLUE;
            p->lchild = pre;
        }
        if(pre && !pre->rchild){
            pre->rFlag = flag::CLUE;
            pre->rchild = p;
        }
        //cout<<p->value<<", 前驱="<<p->lchild->value<<",后继="<<p->rchild->value<<'\n';
        pre = p;
        inorder(p->rchild, pre);
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
        if(n->lchild)n->lFlag = flag::LINK;
        if(n->rchild)n->rFlag = flag::LINK;
        return n;
    }
public:
    threadedBt():root(NULL){}
    ~threadedBt(){del(root);}
    threadedBt(int pre[], int in[], size_t preSize, size_t inSize){//已知先序和中序遍历，构造树
        root = createBst(pre, in, 0, preSize-1, 0, inSize-1);
    }
    void printPreorder(){
        cout<<"先序线索化二叉树构造\n";
        node* pre = NULL;
        preorder(root, pre);
        cout<<"顺序遍历线索化二叉树\n";
        node* p = root;

        while(p){
            while(p->lFlag != flag::CLUE){
                cout << p->value << ',';
                p = p->lchild;
            }
            cout << p->value << ',';
            p = p->rchild;
        }
    }
    void printInorder(){
        cout<<"中序线索化二叉树构造\n";
        node* pre = NULL;
        inorder(root, pre);
        cout<<"顺序遍历线索化二叉树\n";
        node* p = root;

        while(p){
            while(p->lFlag != flag::CLUE){
                p = p->lchild;
            }
            cout << p->value << ',';
            while(p->rFlag == flag::CLUE){
                p = p->rchild;
                cout << p->value << ',';
            }
            p = p->rchild;
        }
    }
};
int main(){
    int pre[]={1,2,4,5,3,6};
    int in []={4,2,5,1,3,6};
    threadedBt b(pre, in, sizeof(pre)/sizeof(int), sizeof(in)/sizeof(int));
    b.printPreorder();
    threadedBt b2(pre, in, sizeof(pre)/sizeof(int), sizeof(in)/sizeof(int));
    b2.printInorder();
    return 0;
}
