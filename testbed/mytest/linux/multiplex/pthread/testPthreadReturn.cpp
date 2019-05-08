#include<pthread.h>
#include<unistd.h>
#include<iostream>
using namespace std;
int retval=70;
void* tf(void*arg) {
    string *s = new string;
    *s = "abc";
    return s;
}
int main() {
    pthread_t tid;
    pthread_create(&tid, NULL, tf, NULL);
    string *pval;
    pthread_join(tid, (void **) &pval);
    cout << *pval << endl;
    delete pval;
    return 0;
}