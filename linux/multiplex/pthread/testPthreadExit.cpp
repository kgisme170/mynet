#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
#include<exception>
using namespace std;
struct S{
    S(){printf("ctor\n");}
    ~S(){printf("dtor\n");}
};
void* tf(void*arg){
    try{
        pthread_exit(NULL);
    }catch(...){
        printf("捕获异常\n");
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
