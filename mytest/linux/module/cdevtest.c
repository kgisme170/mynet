#include <linux/module.h>
#include <linux/init.h>
#include <linux/fs.h>
#include <linux/wait.h>
#include <linux/semaphore.h>
#include <linux/sched.h>
#include <linux/cdev.h>
#include <linux/types.h>
#include <linux/kdev_t.h>
#include <linux/device.h>
#include <asm/uaccess.h>
MODULE_LICENSE("GPL");
static struct class *cls = NULL;

static int major = 0;
static int minor = 0;
const  int count = 6;

#define DEVNAME "mydemo"
static struct cdev *demop = NULL;

//打开设备
static int demo_open(struct inode *inode, struct file *filp) {
    //get command and pid
    printk(KERN_INFO
    "module open: %s : %s : %d\n", __FILE__, __func__, __LINE__);
    return 0;
}
//关闭设备
static int demo_release(struct inode *inode, struct file *filp) {
    //get major and minor from inode
    printk(KERN_INFO
    "module release: %s : %s : %d\n", __FILE__, __func__, __LINE__);
    return 0;
}
//读设备
static ssize_t demo_read(struct file *filp, char __user *buf, size_t size, loff_t *offset) {
    struct inode *inode = filp->f_path.dentry->d_inode;
    //get command and pid
    printk(KERN_INFO "read request (%s:pid=%d), %s : %s : %d, size = %ld\n", current->comm, current->pid, __FILE__, __func__, __LINE__, size);
    //get major and minor from inode
    printk(KERN_INFO "(major=%d, minor=%d), %s : %s : %d\n", imajor(inode), iminor(inode), __FILE__, __func__, __LINE__);
    return 0;
}
//写设备 
static ssize_t demo_write(struct file *filp, const char __user *buf, size_t size, loff_t *offset) {
    char buffer[128];
    struct inode *inode = filp->f_path.dentry->d_inode;
    //get command and pid
    printk(KERN_INFO "write request(%s:pid=%d), %s : %s : %d, size = %ld\n", current->comm, current->pid, __FILE__, __func__, __LINE__, size);
    //get major and minor from inode
    printk(KERN_INFO "(major=%d, minor=%d), %s : %s : %d\n", imajor(inode), iminor(inode), __FILE__, __func__, __LINE__);
    if(raw_copy_from_user(buffer, buf, size)){
        return -EFAULT;
    }
    buffer[size-1]='\0';
    printk(KERN_INFO "user wrote bytes=%s", buffer);
    return size;
}
static struct file_operations fops = {
    .owner   = THIS_MODULE,
    .open    = demo_open,
    .release = demo_release,
    .read    = demo_read,
    .write   = demo_write
};

static int __init demo_init(void){
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

static void __exit demo_exit(void){
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