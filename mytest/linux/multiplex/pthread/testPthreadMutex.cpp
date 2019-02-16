#include<pthread.h>
#include<unistd.h>
#include<cassert>
#include<iostream>
using namespace std;
pthread_mutex_t mt=PTHREAD_MUTEX_INITIALIZER;
pthread_t tid[2];
char msg1[]="thread1";
char msg2[]="thread2";
void* tf(void*arg) {
    pthread_mutex_lock(&mt);
    cout << (char *) arg << endl;
    pthread_mutex_unlock(&mt);
    return NULL;
}
int main() {
    assert(0 == pthread_mutex_init(&mt, NULL));
    pthread_create(&tid[0], NULL, tf, msg1);
    pthread_create(&tid[1], NULL, tf, msg2);
    pthread_join(tid[0], NULL);
    pthread_join(tid[1], NULL);
    return 0;
}