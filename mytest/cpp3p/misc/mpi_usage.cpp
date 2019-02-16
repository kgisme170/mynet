#include "mpi.h"
#include <stdio.h>
#include <math.h>
#include <unistd.h>
#include <iostream>
using namespace std;//本程序来自网络
int main(int argc, char *argv[]) {
    int namelen;
    char processor_name[MPI_MAX_PROCESSOR_NAME];
    MPI_Init(&argc, &argv);
    int myid;     // 当前进程编号
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    MPI_Get_processor_name(processor_name, &namelen);
    cout << "处理器名称=" << processor_name << '\n';
    if (myid == 0) {
        MPI_Send("Hello", 6, MPI_CHAR, 1, 99, MPI_COMM_WORLD);
        printf("发送到1\n");
    } else if (myid == 1) {
        char buf[10];
        MPI_Status status;
        MPI_Recv(buf, 10, MPI_CHAR, 0, 99, MPI_COMM_WORLD, &status);
        printf("接受来自0 %s\n", buf);
    }
    for (int i = 0; i < 4; ++i) {
        cout << "I'm" << myid << ", sending " << i << endl;
        sleep(1);
        if (0 == myid && 0 == i) {
            MPI_Barrier(MPI_COMM_WORLD); //进程0将一直等待，直到其他并行进程执行结束
        }
    }
    MPI_Finalize();
    return 0;
}