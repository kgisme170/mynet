#include<stdio.h>
#include<stdlib.h>
#include<stdarg.h>
#include<string.h>
char* make_path(int count, ...){
    va_list args;
    va_start(args, count);
   
    size_t index=0;
    char* p=NULL;
    char* buffer=(char*)malloc(1024);
    int i;
    for(i=0;i<count;++i){
        p=va_arg(args,char*);
        puts(p);
        size_t len=strlen(p);
        memcpy(buffer+index, p, len);
        index+=len;
    }
    return buffer;
}
int main(){

    char* p = make_path(3, "/root", "/abc", "/xyz");
    puts(p);
    free(p);
    return 0;
}