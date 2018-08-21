#include<assert.h>
#include<libaio.h>
#include<signal.h>
#include<stdio.h>
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
        io_context_t ctx={0};
        struct iocb io,*p=&io;
        struct io_event e[1];
        assert(io_setup(1,&ctx)==0);

        while(true){
            char buf[1024];
            io_prep_pread(&io,fd[0],(char*)buf,sizeof(buf),0);
            assert(io_submit(ctx,1,&p)==1);
            assert(io_getevents(ctx,1,1,e,NULL)==1);
            printf("读入=%s\n",buf);
        }
    }
    return 0;
}
