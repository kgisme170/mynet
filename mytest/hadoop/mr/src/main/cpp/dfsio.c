#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"hdfs.h"
int main(int argc, char* argv[]){
    hdfsFS fs=hdfsConnect("default",0);
    hdfsFile writeFile=hdfsOpenFile(fs,argv[1],O_WRONLY|O_CREAT,0,0,0);
    if(!writeFile){
        fprintf(stderr,"打开 %s 文件写入，失败\n",argv[1]);
        exit(-1);
    }
    char buffer[]="hw";
    tSize numWrittenBytes=hdfsWrite(fs,writeFile,(void*)buffer,strlen(buffer)+1);
    if(hdfsFlush(fs,writeFile)){
        fprintf(stderr,"flush到文件 %s 失败\n",argv[1]);
        exit(-1);
    }
    hdfsCloseFile(fs, writeFile);
    return 0;
}