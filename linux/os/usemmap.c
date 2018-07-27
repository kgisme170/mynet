#include<sys/mman.h>
#include<sys/types.h>
#include<unistd.h>
#include<wait.h>
#include<stdio.h>
void main(){
    int i,sum;
    int*result_ptr=mmap(0,4,PROT_READ|PROT_WRITE,MAP_SHARED|MAP_ANONYMOUS,0,0);
    int pid=fork();
    if(pid==0){//child
        for(sum=0,i=1;i<=10;++i){sum+=i;}
        *result_ptr=sum;
    }else{
        wait(0);
        printf("result=%d\n",*result_ptr);
    }
}