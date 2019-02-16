#include<assert.h>
#include<pthread.h>
#include<stdio.h>
int main() {
    printf("开始\n");
    pthread_rwlock_t lock;
    pthread_rwlockattr_t attr;
    assert(0 == pthread_rwlockattr_init(&attr));
    /*
    Setting the value read-write lockkind to PTHREAD_RWLOCK_PREFER_WRITER_NP, 
    results in the same behavior assetting the value to PTHREAD_RWLOCK_PREFER_READER_NP. 
    As long as a readerthread holds the lock the thread holding a write lock will be starved. 
    Settingthe kind value to PTHREAD_RWLOCK_PREFER_WRITER_NONRECURSIVE_NP, allows thewriter to run. 
    However, the writer may not be recursive as is implied by the name. 
    */
    assert(0 == pthread_rwlockattr_setkind_np(&attr,
                                              PTHREAD_RWLOCK_PREFER_WRITER_NONRECURSIVE_NP));
    assert(0 == pthread_rwlock_init(&lock, &attr));
    assert(0 == pthread_rwlockattr_destroy(&attr));
    assert(0 == pthread_rwlock_rdlock(&lock));
    assert(0 == pthread_rwlock_rdlock(&lock));
    // assert(0==pthread_rwlock_rdlock(&lock));
    assert(0 == pthread_rwlock_unlock(&lock));
    assert(0 == pthread_rwlock_unlock(&lock));
    printf("释放读锁\n");

    assert(0 == pthread_rwlock_wrlock(&lock));
    // assert(0==pthread_rwlock_wrlock(&lock)); 写锁不能重入
    assert(0 == pthread_rwlock_unlock(&lock));
    assert(0 == pthread_rwlock_destroy(&lock));
    printf("结束\n");
    return 0;
}