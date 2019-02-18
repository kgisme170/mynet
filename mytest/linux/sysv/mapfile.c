#include<sys/mman.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<stdio.h>
#include<fcntl.h>
int main(){
    int i = 15;
    int fd=open("test.dat", O_RDWR|O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    write(fd, &i, 4);
    int*result_ptr=mmap(0,4,PROT_READ|PROT_WRITE,MAP_SHARED,fd,0);
    printf("result_ptr=%p\n",result_ptr);
    *result_ptr=15;
    printf("result=%d\n",*result_ptr);
    munmap(result_ptr,4);
    printf("munmap ok\n");
    close(fd);
}
