#include <stdio.h>
#include <string>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
void* demo_x(void*) {
    std::string *a = new std::string;
    char *x = new char[1024];
    delete a;
    delete[] x;
    return NULL;
}

int demo_y(int idx) {
    std::string *a = new std::string;
    char *x = new char[1024];
    memset(x, idx, 1024);
    if (idx % 3 == 0) delete[] x;
    return a->size() + idx;
}

int main(int argc, char *argv[]) {
    int ar[1000000];
    int rt = 0;
    demo_x(NULL);
    pthread_t th1;
    (void) pthread_create(&th1, NULL, demo_x, NULL);
    for (size_t idx = 0; idx < 1 * 1024 * 1024; ++idx)
        rt += demo_y(idx);
    (void) pthread_join(th1, NULL);
    abort(); // just use for obtain memory layout
    return rt;
}