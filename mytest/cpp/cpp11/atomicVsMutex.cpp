#include<iostream>
#include<atomic>
#include<mutex>
#include<thread>
#include<chrono>
#include<limits>
using namespace std;
const int INT_MAX = numeric_limits<std::int32_t>::max();
const int INT_MIN = numeric_limits<std::int32_t>::min();
using std::chrono::steady_clock;
//CRC算法
const size_t LOOP_COUNT = 12500000;
const size_t THREAD_COUNT = 8;
int intArray[2]={0,INT_MAX};
atomic<int> atomicArray[2];
void atomic_tf(){//3.9s
    for(size_t i=0;i<LOOP_COUNT;++i){
        atomicArray[0]++;
        atomicArray[1]--;
    }
}
void mutex_tf(){//0.57s
    for(size_t i=0;i<LOOP_COUNT;++i){
        intArray[0]++;
        intArray[1]--;
    }
}
int main(){
    atomicArray[0] = 0;
    atomicArray[1] = INT_MAX;

    thread tp[THREAD_COUNT];
    steady_clock::time_point t1 = steady_clock::now();
    for(size_t t=0;t<THREAD_COUNT;++t){
        tp[t] = thread(mutex_tf);
    }
    for(size_t t=0;t<THREAD_COUNT;++t){
        tp[t].join();
    }
    steady_clock::time_point t2 = steady_clock::now();
    cout<<(float)((t2-t1).count())/1000000000<<endl;
    return 0;
}
