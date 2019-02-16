//#include<gnu/libc-version.h>
#include<spawn.h>
#include<stdio.h>
#include<stdlib.h>
#include<sys/wait.h>
int main() {
    //puts (gnu_get_libc_version ());
    char arg[] = "-l";
    char *argv[] = {arg};
    pid_t pid;
    posix_spawn(&pid, "ls", NULL, NULL, argv, NULL);
    printf("子进程id=%d\n", pid);
    int status;
    wait(&status);
    return 0;
}