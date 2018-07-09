#include <iostream>
using namespace std;
/*功能: roundrobinArrayQueue
 *用数组实现一个循环队列，实现插入和删除, tag表示队列是空(false)还是满(true)
 */
const size_t qSize = 3;
struct rraq{
    int buf[qSize];
    size_t frontIdx;
    size_t rearIdx;
    bool tag;
    rraq():frontIdx(qSize-1),rearIdx(qSize-1),tag(false){
        for(size_t i=0;i<qSize;++i){
            buf[i]=-1;
        }
    }
    void push(int val){
        if(frontIdx == rearIdx && tag == true){//满了
            cout<<"队列已经满了,不能push\n";
            return;
        }else{
            rearIdx = (rearIdx+1) % qSize;
            buf[rearIdx] = val;
            if(rearIdx == frontIdx){
                tag = true;
            }
        }
        cout<<frontIdx<<','<<rearIdx<<endl;
    }
    int pop(){
        if(frontIdx == rearIdx && tag == false){//空队列
            cout<<"队列是空的,不能pop\n";
            return -1;
        }else{
            frontIdx = (frontIdx+1) % qSize;
            int ret = buf[frontIdx];
            if(rearIdx == frontIdx){
                tag = false;
            }
            return ret;
        }
    }
};

int main(){
    rraq q;
    q.push(2);
    q.push(3);
    cout<<q.pop()<<","<<q.pop()<<endl;
    return 0;
}
