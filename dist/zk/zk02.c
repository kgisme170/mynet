#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<errno.h>
#include"zookeeper.h"
#include"zookeeper_log.h"

static int connected = 0;
static int expired = 0;
void main_watcher(zhandle_t* zkh,int type,int state,const char* path, void* context){
    if(type==ZOO_SESSION_EVENT){
        if(state==ZOO_CONNECTED_STATE){
            connected = -1;
        }else if(state == ZOO_NOTCONNECTED_STATE){
            connected = 0;
        }else if(state == ZOO_EXPIRED_SESSION_STATE){
            expired = 1;
            connected = 0;
            zookeeper_close(zkh);
        }
    }
}

static int server_id;
int init(char* hostPort){
    srand(time(NULL));
    server_id=rand();
    zoo_set_debug_level(ZOO_LOG_LEVEL_INFO);
    zh=zookeeper_init(hostPort, main_watcher, 15000, NULL, NULL, 0);
    return errno;
}

int main(int argc, const char *argv[])
{
    const char* host = "127.0.0.1:2181";
    int timeout = 30000;
    char buffer[512];
    int *bufferlen;
    printf("初始化\n");
    zoo_set_debug_level(ZOO_LOG_LEVEL_WARN); //设置日志级别,避免出现一些其他信息
    
}
