obj-m += hellomod.o mylist.o
obj-m += print_pid.o
#obj-m += memtest.o
obj-m += phymem.o
#module-objs := mylist.o
KERNEL ?= /lib/modules/$(shell uname -r)/build
all:
	make -C $(KERNEL) M=$(PWD) modules
clean:
	make -C $(KERNEL) M=$(PWD) clean