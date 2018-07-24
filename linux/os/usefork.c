#include<sys/stat.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<fcntl.h>
#include<unistd.h>
#include<stdio.h>
int main(){
    pid_t p = fork();
    int i = 0, status;
    int fd;
    char buf[1024];
    if (p>0){//父
        sleep(2);
        wait(&status);
        fd = open("/dev/zero", O_RDONLY);
        printf("read开始\n");
        while(i<1024000){
            read(fd, buf, 1024);//R状态
            ++i;
        }
        printf("read结束\n");
        close(fd);
    }else if(p==0){
        printf("儿子\n");
        scanf("%d", &i);//S+状态
        printf("输入%d,子进程结束\n", i);//父进程sleep期间Z+状态,直到父进程wait()结束
    }else{
        printf("fork失败\n");
        return 1;
    }
    return 0;
}