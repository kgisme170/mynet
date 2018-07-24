#include <linux/init.h>
#include <linux/module.h>
#include <linux/sched.h>
#include <linux/kernel.h>
#include <linux/list.h>
#include <linux/init_task.h>
MODULE_LICENSE("GPL");
static void print_pid(void){
    struct task_struct *task, *p;
    struct list_head *pos;
    int count=0;
    printk("Hello process begins\n");
    task=&init_task;
    list_for_each(pos,&task->tasks){
        p = list_entry(pos, struct task_struct, tasks);
        count++;
        printk("%d--->%s\n", p->pid, p->comm);
    }
    printk("Total process count=%d\n", count);
}
static int __init hello_init(void)
{
    printk(KERN_ALERT "Hello, world\n");
    print_pid();
    return 0;
}

static void __exit hello_exit(void)
{
    printk(KERN_ALERT "Goodbye, cruel world\n");
}

module_init(hello_init);
module_exit(hello_exit);