#include<assert.h>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<errno.h>
#include<stdarg.h>
#include"zookeeper.h"
#include"zookeeper_log.h"
char* make_path(int count, const char* param1, ...){
    va_list args;
    va_start(args, param1);
    char* buffer=(char*)malloc(1024);
    vsnprintf(buffer, count, param1, args);
    va_end(args);
}
static int connected = 0;
static int expired = 0;
void main_watcher(zhandle_t* zkh,int type,int state,const char* path, void* context){
    if(type==ZOO_SESSION_EVENT){
        if(state==ZOO_CONNECTED_STATE){
            connected = -1;
        //}else if(state == ZOO_NOTCONNECTED_STATE){
        //    connected = 0;
        }else if(state == ZOO_EXPIRED_SESSION_STATE){
            expired = 1;
            connected = 0;
            zookeeper_close(zkh);
        }
    }
}

static int server_id;
zhandle_t* zh;
int init(char* hostPort){
    srand(time(NULL));
    server_id=rand();
    zoo_set_debug_level(ZOO_LOG_LEVEL_INFO);
    zh=zookeeper_init(hostPort, main_watcher, 15000, NULL, NULL, 0);
    return errno;
}
void create_parent(const char* path, const char* value);
void create_parent_completion(int rc, const char* value, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
            create_parent(value, (const char*)data);
            break;
        case ZOK:
            printf("创建父节点%s\n", value);
            break;
        case ZNODEEXISTS:
            printf("节点已经存在\n");
            break;
        default:
            printf("create_parent_completion 遇到其他错误 %d\n", rc);
            break;
    }
}

void create_parent(const char* path, const char* value){
    zoo_acreate(zh,path,value,0,&ZOO_OPEN_ACL_UNSAFE,0,create_parent_completion, NULL);
}

void bootstrap(){
    if(!connected){
        printf("客户端没有连接到ZooKeeper\n");
        return;
    }
    create_parent("/workers", "");
    create_parent("/assign", "");
    create_parent("/tasks", "");
    create_parent("/status", "");
}

void master_create_completion(int rc, const char* value, const void* data);

void run_for_master(){
    if(!connected){
        printf("客户端没有连接到ZooKeeper\n");
        return;
    }
    char server_id_string[9];
    snprintf(server_id_string, 9, "%x", server_id);
    zoo_acreate(zh, "/master", (const char*)server_id_string, sizeof(int), &ZOO_OPEN_ACL_UNSAFE,
        ZOO_EPHEMERAL, master_create_completion, NULL);
}

void master_exists_watcher(zhandle_t* zh, int type, int state, const char* path, void* watcherCtx){
    if(type==ZOO_DELETED_EVENT){
        assert(!strcmp(path, "/master"));
        run_for_master();
    }else{
        printf("监控事件%d\n", type);
    }
}
void master_exists();
void master_exists_completion(int rc, const struct Stat* stat, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            master_exists();
            break;
        case ZOK:
            if(stat==NULL){
                printf("前一个master已经消失, 参选master\n");
                run_for_master();
            }
            break;
        default:
            printf("master_exists_completion失败%d\n", rc);
            break;
    }
}
void master_exists(){
    zoo_awexists(zh, "/master", master_exists_watcher, NULL, master_exists_completion, NULL);
}

void get_tasks();
void get_workers();
void workers_completion(int rc, const struct String_vector* strings, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            get_workers();
            break;
        case ZOK:
            struct String_vector* tmp_workers = removed_and_set(strings, &workers);
            free(tmp_workers);
            get_tasks();
            break;
        default:
            printf("workers_completion失败: %d\n", rc);
            break;
    }
}

void get_workers(){
    zoo_awget_children(zh, "/workers", workers_watcher, NULL, workers_completion, NULL);
}

void take_leadership(){
    get_workers();
}

void master_create_completion(int rc, const char* value, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
            check_master();
            break;
        case ZOK:
            take_leadership();
            break;
        case ZNODEEXISTS:
            master_exists();
            break;
        default:
            printf("runnint for master失败 %d\n", rc);
            break;
    }
}

void tasks_watcher(zhandle_t* zh, int type, int state, const char* path, void* watcherCtx){
    if(type==ZOO_CHILD_EVENT){
        assert(!strcmp(path, "/tasks"));
        get_tasks();
    }else{
        printf("监视事件%d\n", type);
    }
}

struct task_info{
    char* name;
    char* value;
    int value_len;
    char* worker;
};
void task_assignment(struct task_info* task);
void get_task_data(const char* task);
void get_task_data_completion(int rc, const char* value, int value_len, const struct Stat* stat, const void* data){
    int worker_index;
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            get_task_data((const char*)data);
            break;
        case ZOK:
            if(workers!=NULL){
                worker_index=(rand()%workers->count);
                struct task_info* new_task;
                new_task=(struct task_info*)malloc(sizeof(struct task_info));
                new_task->name=(char*)data;
                new_task->value=strndup(value, value_len);
                new_task->value_len=value_len;
                const char* worker_string=workers->data[worker_index];
                new_task->worker=strdup(worker_string);
                task_assignment(new_task);
            }
            break;
        default:
            printf("get_task_data_completion失败\n");
            break;
    }
}
void delete_pending_task(const char* path);
void delete_task_completion(int rc, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            delete_pending_task((const char*)data);
            break;
        case ZOK:
            free((char*)data);
            break;
        default:
            printf("delete_task_completion失败\n");
            break;
    }
}

void delete_pending_task(const char* path){
    if(path==NULL)return;
    char* tmp_path=strdup(path);
    zoo_adelete(zh, tmp_path, -1, delete_task_completion, (const void*)tmp_path);
}

void task_assignment_completion(int rc, const char* value, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            task_assignment((struct task_info*)data);
            break;
        case ZOK:
            if(data!=NULL){
                char* del_path="";
                del_path=make_path(2, "/tasks/", ((struct task_info*)data)->name);
                if(del_path!=NULL){
                    delete_pending_task(del_path);
                }
                free(del_path);
                free((struct task_info*)data);
            }
            break;
        case ZNODEEXISTS:
            printf("assignment已经被创建了%s\n", value);
            break;
        default:
            printf("task_assignment_completion失败\n");
            break;
    }
}

void task_assignment(struct task_info* task){
    char* path=make_path(4, "/assign/", task->worker, "/", task->name);
    zoo_acreate(zh,path,task->value,task->value_len,    
        &ZOO_OPEN_ACL_UNSAFE, 0, task_assignment_completion,(const void*)task);
    free(path);
}

void get_task_data(const char* task){
    if(task==NULL)return;
    char* tmp_task=strndup(task,15);
    char* path=make_path(2, "/tasks", tmp_task);
    zoo_aget(zh,path,0,get_task_data_completion,(const void*) tmp_task);
    free(path);
}

void assign_tasks(const struct String_vector* strings){
    int i;
    for(i=0;i<strings->count;++i){
        get_task_data(strings->data[i]);
    }
}

void task_completion(int rc, const struct String_vector* strings, const void* data);
void get_tasks(){
    zoo_awget_children(zh, "/tasks", tasks_watcher, NULL, task_completion, NULL);
}

void task_completion(int rc, const struct String_vector* strings, const void* data){
    switch(rc){
        case ZCONNECTIONLOSS:
        case ZOPERATIONTIMEOUT:
            get_tasks();
            break;
        case ZOK:
            printf("分配tasks\n");
            struct String_vector* tmp_tasks = added_and_set(strings, &tasks);
            assign_tasks(tmp_tasks);
            free(tmp_tasks);
            break;
        default:
            printf("task_completion失败\n");
            break;
    }
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
