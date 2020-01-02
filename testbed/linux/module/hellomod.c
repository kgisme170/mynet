#include <linux/init.h>
#include <linux/module.h>
#include <linux/init_task.h>
MODULE_LICENSE("GPL");
int doublelist_init(void);
static int __init hello_init(void)
{
    printk(KERN_ALERT "Hello, world\n");
    doublelist_init();
    printk(KERN_ALERT "The process is %s, pid=%d\n",current->comm,current->pid);
    return 0;
}
void doublelist_exit(void);
static void __exit hello_exit(void)
{
    doublelist_exit();
    printk(KERN_ALERT "Goodbye, cruel world\n");
}

module_init(hello_init);
module_exit(hello_exit);