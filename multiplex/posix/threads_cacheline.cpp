#include<pthread.h>
#include<stdlib.h>
struct M{
    long a;
    char cache[128];
    long b;
}obj;
size_t count=0;
void* addx(void*args){
    long*pl=(long*)args;
    for(size_t i=0;i<count;++i)
        (*pl)*=i;
    return NULL;
}
int main(int argc,char*argv[]){
    count=argc==1? 2000000000:atol(argv[1]);
    pthread_t tid[2];
    pthread_create(&tid[0],NULL,addx,&obj.a);
    pthread_create(&tid[1],NULL,addx,&obj.b);
    pthread_join(tid[0],NULL);
    pthread_join(tid[1],NULL);
    return 0;
}
