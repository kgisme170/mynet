#include<signal.h>
#include<stdio.h>
#include<unistd.h>
void sigHandler(int signo){
    printf("handler\n");
    alarm(1);
}
int main(){
    signal(SIGALRM,sigHandler);
    alarm(1);
    while(true){pause();}
    return 0;
}
