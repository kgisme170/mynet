#include <iostream>
using namespace std;
/*功能: 设计二叉树算法，在构造的时候增加双亲节点*/
struct node{
    int value;
    node* lchild;
    node* rchild;
    node* parent;
    node(int v):value(v),lchild(NULL),rchild(NULL),parent(NULL){}
    node(int v, node* p):value(v),lchild(NULL),rchild(NULL),parent(p){}
};
class bt{
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
        if(p->lchild){
            preorder(p->lchild);
        }if(p->rchild){
            preorder(p->rchild);
        }
    }
    node* createBst(int pre[], int in[],
                    size_t l1/*pre范围左界*/,
                    size_t r1/*pre范围右界*/,
                    size_t l2/*in范围左界*/,
                    size_t r2/*in范围右界*/,
                    node* parent){
        if(l1>r1)return NULL;//要处理的pre子树长度=0，返回空指针
        size_t i;
        for(i=l2;i<r2;++i){
            if(in[i]==pre[l1])break;
        }//左子树的元素个数是i-l2-1个, 因此新的r1是l1+1+(i-l2-1)=l1+i-l2
        node* n = new node(pre[l1], parent);
        //cout<<"n="<<n<<",value="<<n->value<<'\n';
        n->lchild = createBst(pre, in, l1+1, l1+i-l2, l2, i-1, n);//pre和in分别取左子树
        n->rchild = createBst(pre, in, l1+i-l2+1, r1, i+1, l2, n);//pre和in分别取右子树
        return n;
    }
public:
    bt():root(NULL){}
    ~bt(){del(root);}
    bt(int pre[], int in[], size_t preSize, size_t inSize){//已知先序和中序遍历，构造树
        root = createBst(pre, in, 0, preSize-1, 0, inSize-1, NULL);
    }
    void printPreorder(){
        cout<<"先序";
        preorder(root);
        cout<<'\n';
    }
};
int main(){
    int pre[]={1,2,4,5,3,6};
    int in []={4,2,5,1,3,6};
    bt b(pre, in, sizeof(pre)/sizeof(int), sizeof(in)/sizeof(int));
    b.printPreorder();
    return 0;
}
