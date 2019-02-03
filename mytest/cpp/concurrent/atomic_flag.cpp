#include<atomic>
#include<chrono>
#include<iostream>
#include<thread>
using namespace std;
atomic<bool> ready(false);
atomic_flag winner = ATOMIC_FLAG_INIT;
void tf(int id){
    while(!ready){
        this_thread::yield();
    }
    if(!winner.test_and_set()){
        cout<<"thread number "<<id<<" wins\n";
    }
}
void littleSleep(chrono::microseconds ms){
    auto start = chrono::high_resolution_clock::now();
    auto end = start + ms;
    do{
        this_thread::yield();
    }while(chrono::high_resolution_clock::now() < end);
}
int main(){
    thread tb[10];
    for(size_t i=0;i<10;++i){
        tb[i]=thread(tf, i+1);
    }
    ready = true;
    for(size_t i=0;i<10;++i){
        tb[i].join();
    }
    auto start = chrono::high_resolution_clock::now();
    littleSleep(chrono::microseconds(1000));
    auto d = chrono::high_resolution_clock::now() - start;
    cout<<chrono::duration_cast<chrono::microseconds>(d).count()<<endl;
    return 0;
}
