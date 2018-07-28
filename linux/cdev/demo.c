#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/cdev.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <asm/current.h>
#include <linux/sched.h>
#include <linux/device.h>

MODULE_LICENSE("GPL");

static struct class *cls = NULL;

static int major = 0;
static int minor = 0;
const  int count = 6;

#define DEVNAME    "demo"

static struct cdev *demop = NULL;

//打开设备
static int demo_open(struct inode *inode, struct file *filp)
{
    //get command and pid
    printk(KERN_INFO "%s : %s : %d\n", __FILE__, __func__, __LINE__);return 0;
}

//关闭设备
static int demo_release(struct inode *inode, struct file *filp)
{
    //get major and minor from inode
    printk(KERN_INFO "%s : %s : %d\n", __FILE__, __func__, __LINE__);
    return 0;
}

static struct file_operations fops = {
    .owner    = THIS_MODULE,
    .open    = demo_open,
    .release= demo_release,
};

static int __init demo_init(void)
{
    dev_t devnum;
    int ret, i;
    struct device *devp = NULL;

    //1. alloc cdev obj
    demop = cdev_alloc();
    if(NULL == demop){
        return -ENOMEM;
    }
    //2. init cdev obj
    cdev_init(demop, &fops);

    ret = alloc_chrdev_region(&devnum, minor, count, DEVNAME);
    if(ret){
        goto ERR_STEP;
    }
    major = MAJOR(devnum);

    //3. register cdev obj
    ret = cdev_add(demop, devnum, count);
    if(ret){
        goto ERR_STEP1;
    }
    cls = class_create(THIS_MODULE, DEVNAME);
    if(IS_ERR(cls)){
        ret = PTR_ERR(cls);
        goto ERR_STEP1;
    }
    for(i = minor; i < (count+minor); i++){
        devp = device_create(cls, NULL, MKDEV(major, i), NULL, "%s%d", DEVNAME, i);
        if(IS_ERR(devp)){
            ret = PTR_ERR(devp);
            goto ERR_STEP2;
        }
    }
    return 0;

ERR_STEP2:
    for(--i; i >= minor; i--){
        device_destroy(cls, MKDEV(major, i));
    }
    class_destroy(cls);

ERR_STEP1:
    unregister_chrdev_region(devnum, count);

ERR_STEP:
    cdev_del(demop);

    //get command and pid
    printk(KERN_INFO "%s : %s : %d - fail.\n", __FILE__, __func__, __LINE__);
    return ret;
}

static void __exit demo_exit(void)
{
    int i;
    //get command and pid
    printk(KERN_INFO "%s : %s : %d - leave.\n", __FILE__, __func__, __LINE__);

    for(i=minor; i < (count+minor); i++){
        device_destroy(cls, MKDEV(major, i));
    }
    class_destroy(cls);

    unregister_chrdev_region(MKDEV(major, minor), count);

    cdev_del(demop);
}

module_init(demo_init);
module_exit(demo_exit);