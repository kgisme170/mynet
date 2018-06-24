#include<stdio.h>
#include<stdlib.h>
#include<stdarg.h>
#include<string.h>
char* make_path(int count, const char* param1, ...){
    va_list args;
    char* p=(char*)args;
    va_start(args, param1);
    char* buffer=(char*)malloc(1024);
    va_arg(args, char*);
    int i;
    for(i=0;i<count;++i){
        size_t len = strlen((char*)args);
        printf("%s\n", (char*)args);/*什么也么有打印*/
        vsnprintf(buffer, len, param1, args);
        p+=len;
        p+=1;
    }
    return buffer;
}
int main(){
    char* p = make_path(3, "/root", "/abc", "/xyz");
    puts(p);
    free(p);
    return 0;
}