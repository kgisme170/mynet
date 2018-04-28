#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
int main(){
    int fd[2];
    pipe(fd);

    pid_t pid=fork();
    if(pid==0){//子进程
        printf("子进程cat\n");
        dup2(fd[1], 1);
        close(fd[0]);
        execlp("cat","cat","tmp",NULL);
        exit(0);
    }else{//父进程
        dup2(fd[0],0);
        close(fd[1]);
        printf("父进程sort\n");
        execlp("sort","sort",NULL);
    }
    return 0;
}