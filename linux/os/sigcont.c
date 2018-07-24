#include<stdio.h>
#include<fcntl.h>
#include<sys/types.h>
#include<unistd.h>
int main(){
    char buf[1024];
    int fd = open("/dev/zero", O_RDONLY);
    while(1){
        read(fd, buf, 1024);//kill -19 进入T状态， kill -18恢复R状态
    }
    close(fd);
    return 0;
}