#include<assert.h>
#include<signal.h>
#include<stdio.h>
#include<poll.h>
#include<sys/wait.h>
#include<unistd.h>
int fd[2];
const char msg[]={'m','e','s','s','a','g','e'};
void sigHandler(int){
    alarm(1);
    ssize_t bytes=write(fd[1],msg,sizeof(msg));
    printf("子进程msg已写入:%ld字节\n",bytes);
}
int main(){
    assert(pipe(fd)==0);
    pid_t pid=fork();
    if(pid==0){//子进程
        signal(SIGALRM,sigHandler);
        alarm(1);
        while(true){pause();}
    }else if(pid>0){//父进程
        pollfd fds[1];
        fds[0].fd=fd[0];
        fds[0].events=POLLIN;
        char buf[1024];
        while(true){
            poll(fds,1,-1);
            if(fds[0].revents&POLLIN){
                ssize_t bytes=read(fd[0],buf,sizeof(buf));
                printf("父进程读入%ld字节=%s\n",bytes,buf);
            }
        }//end while
        int status;
        wait(&status);
    }
    return 0;
}
