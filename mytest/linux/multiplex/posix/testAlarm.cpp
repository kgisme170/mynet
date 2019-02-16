#include<signal.h>
#include<stdio.h>
#include<unistd.h>
void sigHandler(int signo) {
    printf("handler\n");
    alarm(1);
}
void main() {
    signal(SIGALRM, sigHandler);
    alarm(1);
    while (true) { pause(); }
}