#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
pthread_key_t key = 0;
pthread_key_t nosuchkey = 0;

void deletemsg(void *arg) {
    printf("释放tls资源,thread=%ld,arg=%p,value=%ld\n",
           (unsigned long) pthread_self(), arg, (unsigned long) arg);
}

void* _tf(void*arg) {
    static __thread int i = 0;
    ++i;
    printf("%d\n", i);
    return NULL;
}
void* tf(void*arg) {
    pthread_t tid;
    tid = pthread_self();
    printf("本线程id=%lu\n", (unsigned long) tid);
    pthread_setspecific(key, (void *) tid);
    return NULL;
}

int main() {
    pthread_t t[2];
    pthread_key_create(&key, deletemsg);
    pthread_create(&t[0], NULL, tf, NULL);
    sleep(1);
    pthread_create(&t[1], NULL, tf, NULL);
    pthread_join(t[0], NULL);
    pthread_join(t[1], NULL);
    sleep(1);
    pthread_key_delete(key);

    //key 必须创建了以后，并且set了才有有效值.linux下只创建一个key，getspecific还是nil，除非setspecific之后才有值
    void *p = pthread_getspecific(nosuchkey);
    printf("p=%p\n", p);
    pthread_key_create(&nosuchkey, NULL);
    p = pthread_getspecific(nosuchkey);
    printf("p=%p\n", p);
    pthread_setspecific(nosuchkey, (void *) 1);
    p = pthread_getspecific(nosuchkey);
    printf("p=%p\n", p);
    return 0;
}