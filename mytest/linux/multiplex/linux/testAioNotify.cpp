#include<assert.h>
#include<libaio.h>
#include<pthread.h>
#include<signal.h>
#include<stdio.h>
#include<unistd.h>
int fd[2];
const char msg[]={'m','e','s','s','a','g','e'};
pthread_t ntid;
char buf[1024];
struct iocb io,*p=&io;
io_context_t ctx={0};
void* thread_write(void* arg) {
    while (true) {
        ssize_t bytes = write(fd[1], msg, sizeof(msg));
        printf("写入:%ld字节\n", bytes);
        sleep(1);
    }
}
void read_done(io_context_t ctx,struct iocb* ,long,long) {
    printf("读取=%s\n", buf);
}
int main() {
    assert(pipe(fd) == 0);
    pthread_create(&ntid, NULL, &thread_write, NULL);
    assert(io_setup(1, &ctx) == 0);
    while (true) {
        io_prep_pread(&io, fd[0], (char *) buf, sizeof(buf), 0);
        io_set_callback(p, read_done);
        assert(io_submit(ctx, 1, &p) == 1);
        io_queue_run(ctx);
    }
    return 0;
}
