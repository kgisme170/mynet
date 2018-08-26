obj-m += hellomod.o
obj-m += print_pid.o
#obj-m += memtest.o
obj-m += phymem.o
obj-m += cdevtest.o
module-objs := hellomod.o mylist.o
KERNEL ?= /lib/modules/$(shell uname -r)/build
all:
	make -C $(KERNEL) M=$(PWD) modules
clean:
	make -C $(KERNEL) M=$(PWD) clean