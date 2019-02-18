#include<sys/stat.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<fcntl.h>
#include<unistd.h>
#include<stdio.h>
int main(){
    pid_t p = vfork();
    int i = 0, status;
    if (p>0){//父
        sleep(2);
        wait(&status);//父进程处于D状态, ps显示的WCHAN是do_for
    }else if(p==0){
        printf("儿子\n");
        scanf("%d", &i);//S+状态
        printf("输入%d,子进程结束\n", i);//父进程sleep期间Z+状态,直到父进程wait()结束
        execl("/bin/ls", "ls", "-l", NULL);
    }else{
        printf("vfork失败\n");
        return 1;
    }
    return 0;
}
