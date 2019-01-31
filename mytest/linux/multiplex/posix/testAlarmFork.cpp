#include<signal.h>
#include<stdio.h>
#include<sys/wait.h>
#include<unistd.h>
void sigHandler(int signo){
    printf("handler\n");
    alarm(1);
}
int main(){
    pid_t pid=fork();
    if(pid==0){//子进程
        signal(SIGALRM,sigHandler);
        alarm(1);
        while(true){pause();}
    }else if(pid>0){//父进程
        int status;
        wait(&status);
    }
    return 0;
}
