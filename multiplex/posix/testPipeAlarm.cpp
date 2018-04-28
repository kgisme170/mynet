#include<assert.h>
#include<signal.h>
#include<stdio.h>
#include<sys/wait.h>
#include<unistd.h>
int fd[2];
const char msg[]={'a','p','s','a','r','a'};
void sigHandler(int){
    printf("handler\n");
    alarm(1);
    ssize_t bytes=write(fd[1],msg,sizeof(msg));
    printf("msg已写入%ld字节\n",bytes);
}
int main(){
    assert(pipe(fd)==0);
    pid_t pid=fork();
    if(pid==0){//子进程
        signal(SIGALRM,sigHandler);
        alarm(1);
        while(true){pause();}
    }else if(pid>0){//父进程
        sleep(3);
        char buf[1024];
        ssize_t bytes=read(fd[0],buf,sizeof(buf));
        printf("读入%ld字节=%s\n",bytes,buf);
        int status;
        wait(&status);
    }
    return 0;
}
