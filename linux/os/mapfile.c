/*
#include<sys/mman.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<stdio.h>
#include<fcntl.h>
void main(){
    int i,sum;
    int fd=open("test.dat", O_RDWR|O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    int*result_ptr=mmap(0,4,PROT_READ|PROT_WRITE,MAP_SHARED,fd,0);
    printf("result_ptr=%p\n",result_ptr);
    *result_ptr=15;
    printf("result_ptr ok\n");
    munmap(result_ptr,4);
    close(fd);
    printf("result=%d\n",*result_ptr);
}*/
#include <sys/mman.h>  
#include <sys/types.h>  
#include <fcntl.h>  
#include<stdio.h>  
#include <unistd.h>  
typedef struct{  
char name[4];  
int age;  
}people;  
  
void main(int argc, char** argv) // map a normal file as shared mem:  
{  
    int fd,i;  
    people *p_map;  
    fd=open( argv[1],O_CREAT|O_RDWR,00777 );  
  
    p_map = (people*)mmap(0,sizeof(people)*15,PROT_READ|PROT_WRITE,MAP_SHARED,fd,0);
    close(fd);  
    //在映射完后就可以立刻关闭文件了，打开文件是为了获取文件秒速符来映射文件，  
    //文件是否打开对映射内存的操作没有关系。  
      
    for(i = 0;i<15;i++)    
        printf( "name: %s age %d\n",(*(p_map+i)).name, (*(p_map+i)).age );  
  
    munmap( p_map,sizeof(people)*10 );  
}