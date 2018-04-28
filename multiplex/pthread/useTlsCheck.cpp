#include<pthread.h>
#include<unistd.h>
#include<iostream>
#include"useTlsCreate.h"
using namespace std;
pthread_key_t p_key = 10000001;

void* tf(void*arg){
    pthread_t tid;
    tid=pthread_self();
    {
        M obj1;
        sleep(1);
        cout<<"其他线程:"<<pthread_getspecific(p_key)<<endl;
    }
    sleep(2);
    cout<<"其他线程:"<<pthread_getspecific(p_key)<<endl;
    return NULL;
}

int main(){
    pthread_t t;
    pthread_create(&t,NULL,tf,NULL);
    int * p = (int*)pthread_getspecific(p_key);
    cout<<"main:"<<p<<endl;//应该打印0
    sleep(2);
    p = (int*)pthread_getspecific(p_key);
    cout<<"main:"<<p<<endl;//仍然打印0
    pthread_join(t,NULL);
    return 0;
}
