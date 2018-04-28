#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/un.h>
#include <unistd.h>
#define ERR_EXIT(m) { \
    perror(m); \
    exit(EXIT_FAILURE); \
}
iovec vec={0};
msghdr msg={0};

void send_fd(int sock_fd, int number){
    vec.iov_base = &number;
    vec.iov_len = sizeof(number);

    int ret = sendmsg(sock_fd, &msg, 0);
    if (ret == -1)
        ERR_EXIT("sendmsg");
}
int recv_fd(const int sock_fd)
{
    int number;
    vec.iov_base = &number;
    vec.iov_len = sizeof(number);

    int ret = recvmsg(sock_fd, &msg, 0);
    if (ret == 1)
        ERR_EXIT("recvmsg");
    int rc = *(int*)msg.msg_iov[0].iov_base;
    printf("%d\n", rc);
    return rc;
}
int main(void){
    int sockfds[2];
    if (socketpair(PF_UNIX, SOCK_STREAM, 0, sockfds) < 0){
        ERR_EXIT("socketpair");
    }
    msg.msg_name = NULL;
    msg.msg_namelen = 0;
    msg.msg_iov = &vec;
    msg.msg_iovlen = 1;
    msg.msg_flags = 0;
    int pid=fork();
    if(pid==0){//child
        send_fd(sockfds[1], 20);
    }else if(pid>0){//father
        recv_fd(sockfds[0]);
    }else{
        printf("error\n");
    }
    return 0;
}
