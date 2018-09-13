#include <fcntl.h>
#include <signal.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
static volatile int event_fd;
static void handler(int signum, siginfo_t *si, void *data){
    event_fd = si->si_fd;
    char msg[100];
    sprintf(msg, "info size:%ld, data:%ld\n", sizeof(siginfo_t), sizeof(data));
    write(STDOUT_FILENO, msg, strlen(msg));
}
int main(int argc, char **argv){
    struct sigaction action;
    int fd;
    action.sa_sigaction = handler;
    sigemptyset(&action.sa_mask);
    action.sa_flags = SA_SIGINFO;
    sigaction(SIGRTMIN+1, &action, NULL);

    fd = open("./test.txt", O_RDONLY);
    fcntl(fd, F_SETSIG, SIGRTMIN+1);
    fcntl(fd, F_NOTIFY, DN_MODIFY | DN_CREATE | DN_MULTISHOT);

    fd = open(".", O_RDONLY);
    fcntl(fd, F_SETSIG, SIGRTMIN+1);
    fcntl(fd, F_NOTIFY, DN_MODIFY | DN_CREATE | DN_MULTISHOT);
    while(1){
        pause();
        char msg[100];
        sprintf(msg, "got event on fd=%d\n", event_fd);
        write(STDOUT_FILENO, msg, strlen(msg));
    }
}