#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
void* tf(void*arg){
    int oldstate;
    int i=0;
    pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
    while(1){
        printf("sleep1\n");
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
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
