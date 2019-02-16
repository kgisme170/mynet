#include<pthread.h>
#include<stdlib.h>
struct M {
    long a;
    long b;
} obj;
size_t count=0;
void* addx(void*args) {
    M *obj = (M *) args;
#pragma omp parallel for
    for (size_t i = 0; i < count; ++i) {
        obj->a *= i;
        obj->b *= i;
    }
    return NULL;
}
int main(int argc,char*argv[]) {
    count = argc == 1 ? 2000000000 : atol(argv[1]);
    pthread_t tid;
    pthread_create(&tid, NULL, addx, &obj);
    pthread_join(tid, NULL);
    return 0;
}