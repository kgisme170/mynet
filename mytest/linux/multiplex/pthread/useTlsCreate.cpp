#include"useTlsCreate.h"
#include<pthread.h>
extern pthread_key_t p_key;
static void threadDestructor(void *st) {
    pthread_key_delete(p_key);
}
M::M() {
    pthread_key_create(&p_key, threadDestructor);
    pthread_setspecific(p_key, (void *) 1012);
}
M::~M() {
    pthread_setspecific(p_key, NULL);
}
