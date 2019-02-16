#include<pthread.h>
#include<stdlib.h>
struct M {
    long a;
    pthread_rwlock_t rwLock;
} obj;
size_t count=0;
void* adda(void*args) {
    pthread_rwlock_wrlock(&obj.rwLock);
    for (size_t i = 0; i < count; ++i)
        obj.a *= i;
    pthread_rwlock_unlock(&obj.rwLock);
    return NULL;
}
void* addb(void*args) {
    pthread_rwlock_wrlock(&obj.rwLock);
    for (size_t i = 0; i < count; ++i)
        obj.a *= i;
    pthread_rwlock_unlock(&obj.rwLock);
    return NULL;
}
int main(int argc,char*argv[]) {
    count = argc == 1 ? 2000000000 : atol(argv[1]);
    pthread_t tid[2];
    pthread_create(&tid[0], NULL, adda, NULL);
    pthread_create(&tid[1], NULL, addb, NULL);
    pthread_join(tid[0], NULL);
    pthread_join(tid[1], NULL);
    return 0;
}