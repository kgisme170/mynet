#include<pthread.h>
#include<unistd.h>
#include<cassert>
#include<iostream>
using namespace std;
pthread_mutex_t mt=PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond;
pthread_t tid;
void* tf(void*arg){
    pthread_mutex_lock(&mt);
    pthread_cond_wait(&cond, &mt);
    cout<<"After main thread sleeps 3 seconds\n";
    return NULL;
}
int main(){
    assert(0==pthread_mutex_init(&mt,NULL));
    assert(0==pthread_cond_init(&cond,NULL));
    pthread_create(&tid,NULL,tf,NULL);
    sleep(3);
    cout<<"signal subprocess"<<endl;
    pthread_cond_signal(&cond);
    pthread_join(tid,NULL);
    pthread_cond_destroy(&cond);
    pthread_mutex_destroy(&mt);
    return 0;
}
