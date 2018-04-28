#include<pthread.h>
#include<unistd.h>
#include<iostream>
using namespace std;
void* tf(void*arg){
    int oldstate;
    int i=0;
    pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
    while(1){
        cout<<"sleep1\n";
        sleep(1);
        ++i;
        if(i==3){
            pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
        }
    }
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    cout<<"cancel thread\n";
    pthread_join(tid,NULL);
    return 0;
}
