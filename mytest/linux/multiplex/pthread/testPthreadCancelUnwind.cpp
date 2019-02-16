#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
#include<exception>
using namespace std;
struct S {
    S() { printf("ctor\n"); }

    ~S() { printf("dtor\n"); }
};
void* tf(void*arg) {
    try {
        S obj;
        int oldstate;
        int i = 0;
        pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
        while (1) {
            printf("sleep1\n");
            sleep(1);
            ++i;
            if (i == 3) {
                pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
                break;
            }
        }
    } catch (exception &e) {
        printf("捕获异常%s\n", e.what());
        sleep(1);
        throw e;
    }
    return NULL;
}
int main() {
    pthread_t tid;
    pthread_create(&tid, NULL, tf, NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid, NULL);
    return 0;
}