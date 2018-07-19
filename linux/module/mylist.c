#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/slab.h>
#include <linux/list.h>
MODULE_LICENSE("GPL");
MODULE_AUTHOR("GLM");

#define N 10
struct numlist{
    int num;
    struct list_head list;
};
struct numlist numhead;//
int doublelist_init(void){
    struct numlist *listnode;
    struct list_head * pos;
    struct numlist *p;
    int i;
    printk("doublelist is starting...\n");
    INIT_LIST_HEAD(&numhead.list);
    for(i=0;i<N;++i){
        listnode=(struct numlist*)kmalloc(sizeof(struct numlist),GFP_KERNEL);
        listnode->num=i+1;
        list_add_tail(&listnode->list, &numhead.list);
        printk("Node %d has added to the doublelist...\n", i+1);
    }
    i=1;
    list_for_each(pos, &numhead.list){
        p = list_entry(pos, struct numlist, list);
        printk("Node %d's data: %d\n", i, p->num);
        ++i;
    }
    return 0;
}
void doublelist_exit(void){
    struct list_head *pos, *n;
    struct numlist *p;
    int i;
    i = 1;
    list_for_each_safe(pos, n, &numhead.list){
        list_del(pos);
        p = list_entry(pos, struct numlist, list);
        kfree(p);
        printk("Node %d has been removed from the doublelist...\n", i++);
    }
    printk("doublelist is exiting...\n");
}
EXPORT_SYMBOL(doublelist_init);
EXPORT_SYMBOL(doublelist_exit);