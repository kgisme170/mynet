#include<assert.h>
#include<signal.h>
#include<stdio.h>
#include<sys/epoll.h>
#include<sys/time.h>
#include<sys/wait.h>
#include<unistd.h>
int fd[2];
const char msg[]={'m','e','s','s','a','g','e'};
void sigHandler(int) {
    ssize_t bytes = write(fd[1], msg, sizeof(msg));
    printf("子进程msg已写入:%ld字节\n", bytes);
}
int main() {
    assert(pipe(fd) == 0);
    pid_t pid = fork();
    if (pid == 0) {//子进程
        struct sigaction sa;
        sa.sa_flags = 0;
        sa.sa_handler = sigHandler;
        sigaction(SIGALRM, &sa, NULL);
        itimerval tick = {0};
        tick.it_value.tv_sec = 1;
        tick.it_interval.tv_sec = 1;
        assert(setitimer(ITIMER_REAL, &tick, NULL) == 0);
        while (true) { pause(); }
    } else if (pid > 0) {//父进程
        char buf[1024];
        int epfd = epoll_create(1);
        epoll_event ev, events[1];
        ev.data.fd = fd[0];
        ev.events = EPOLLIN | EPOLLET;
        epoll_ctl(epfd, EPOLL_CTL_ADD, fd[0], &ev);
        while (true) {
            epoll_wait(epfd, events, 1, -1);
            if (events[0].data.fd == fd[0]) {
                ssize_t bytes = read(fd[0], buf, sizeof(buf));
                printf("父进程读入%ld字节=%s\n", bytes, buf);
            }
        }//end while
    }
    return 0;
}
