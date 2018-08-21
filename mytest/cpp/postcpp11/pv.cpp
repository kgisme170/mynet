#include <iostream>
#include <thread>
#include <condition_variable>
#include <mutex>
using namespace std;
/*功能: 测试pv原语*/

class Semaphore{
    mutex m;
    condition_variable c;
    size_t count;
public:
    Semaphore(size_t c=0):count(c){}
    void Wait(){
        unique_lock<mutex> _(m);
        c.wait(_, [=]{return count>0;});
        --count;
    }
    void Signal(){
        unique_lock<mutex> _(m);
        ++count;
        c.notify_one();
    }
};
void P(Semaphore& s){s.Wait();}
void V(Semaphore& s){s.Signal();}

const size_t n = 10;
Semaphore full(0);//满缓冲区数目
Semaphore empty(n);//空缓冲区数目
Semaphore m(1);//对有界缓冲区进行操作的互斥信号量
mutex mCout;//对cout保护
void print(const string& s){
    unique_lock<mutex> _(mCout);
    cout<<s<<'\n';
}
Producer(){
    while(true){
        //Produce an item
        P(empty);//申请一个空缓冲区
        P(m);//申请使用缓冲池
        //将产品放入缓冲区
        V(m);//释放信号量
        V(full);//增加一个满缓冲区
        print("生产者");
        this_thread::sleep_for(1s);
    }
}
Consumer(){
    while(true){
        P(full);//申请一个满缓冲区
        P(m);//申请使用缓冲池
        //取出产品
        V(m);
        V(empty);//增加一个空缓冲区
        //Consume the item
        print("消费者");
        this_thread::sleep_for(1s);
    }
}
int main(){
    thread tc(Consumer);
    thread tp(Producer);
    tc.join();
    tp.join();
    return 0;
}
