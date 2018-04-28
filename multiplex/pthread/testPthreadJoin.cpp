#include<pthread.h>
#include<unistd.h>
#include<iostream>
using namespace std;
void* tf(void*arg){
    cout<<"After main thread sleeps 3 seconds"<<endl;;
    sleep(3);
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(1);
    cout<<"signal subprocess"<<endl;
    pthread_join(tid,NULL);
    return 0;
}
