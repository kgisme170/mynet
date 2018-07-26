#include<linux/module.h>
#include<linux/init.h>
#include<linux/interrupt.h>
#include<linux/sched.h>
#include<linux/pid.h>
static int pid;
module_param(pid, int, 0644);

static inline struct task_struct* find_task_by_pid(int pid){
    struct task_struct*p, ** htable=&pidhash[pid_hashfn(pid)];
    for(p=*htable;p&&p->pid!=pid,p=p->pidhash_next);
    return p;
}
static int __init memtest(void){
    struct task_struct*p;
    struct vm_area_struct*temp;
    printk("The virtual memory areas(VMA) are:\n");
    p=find_task_by_pid(pid);
    temp=p->mm->mmap;
    while(temp){
        printk("start:%p\tend:%p\n", (unsigned long*)temp->vm_start, (unsigned long*)temp->vm_end);
        temp=temp->vm_next;
    }
    return 0;
}
static void __exit memend(void){
    printk("Unload module\n");
}
module_init(memtest);
module_exit(memend);
MODULE_LICENSE("GPL");