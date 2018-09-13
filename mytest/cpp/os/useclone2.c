// #define _GNU_SOURCE   // needed if compiled as C instead of C++
#include <stdio.h>
#include <sched.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>

int f(void*arg)
{
    pid_t pid=getpid();
    printf("child pid=%d\n",pid);
    return 0;
}
char buf[1024*1024];   // *** allocate more stack ***
int main()
{
    printf("before clone\n");
    int pid=clone(f,buf+sizeof(buf),CLONE_VM|CLONE_VFORK,NULL);
         // *** in previous line: pointer is to *end* of stack ***
    if(pid==-1){
        printf("%d\n",errno);
        return 1;
    }
    waitpid(pid,NULL,0);
    printf("after clone\n");
    printf("father pid=%d\n",getpid());
    return 0;
}