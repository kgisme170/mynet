#include<pthread.h>
#include<iostream>
#include<unistd.h>
using namespace std;
typedef int dataType;

const size_t bufferSize = 8;
dataType buffer[bufferSize];
pthread_cond_t notempty;//队列非空
pthread_cond_t notfull;//队列未满
pthread_mutex_t lk;
size_t readpos,writepos;
void init(){
    readpos=0;
    writepos=0;
    pthread_cond_init(&notempty,NULL);
    pthread_cond_init(&notfull,NULL);
    pthread_mutex_init(&lk,NULL);
}
void uninit(){
    pthread_mutex_destroy(&lk);
    pthread_cond_destroy(&notfull);
    pthread_cond_destroy(&notempty);
}
void produce(dataType data){
    pthread_mutex_lock(&lk);
    if((writepos+1)%bufferSize==readpos){
        pthread_cond_wait(&notfull,&lk);
    }
    buffer[writepos]=data;
    ++writepos;
    if(writepos>=bufferSize)writepos=0;
    pthread_cond_signal(&notempty);
    pthread_mutex_unlock(&lk);
}
dataType consume(){
    pthread_mutex_lock(&lk);
    if(readpos==writepos){
        pthread_cond_wait(&notempty,&lk);
    }
    dataType ret = buffer[readpos];
    ++readpos;
    if(readpos>=bufferSize)readpos=0;
    pthread_cond_signal(&notfull);
    pthread_mutex_unlock(&lk);
    return ret;
}
const dataType END=-1;
void* producer(void*){
    for(size_t i=1;i<=100;++i){
        cout<<"生产:"<<i<<endl;
        produce(i);
    }
    produce(END);
    return NULL;
}
void* consumer(void*){
    while(true){
        dataType d=consume();
        if(d==END)break;
        cout<<"消费:"<<d<<endl;
    }
    return NULL;
}

int main(){
    init();
    pthread_t tid[2];
    pthread_create(&tid[0],NULL,&producer,NULL);
    pthread_create(&tid[1],NULL,&consumer,NULL);
    pthread_join(tid[0],NULL);
    pthread_join(tid[1],NULL);
    uninit();
    return 0;
}
