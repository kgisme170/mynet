obj-m += hellomod.o
obj-m += print_pid.o
obj-m += mylist.o
obj-m += phymem.o
obj-m += cdevtest.o
module-objs := hellomod.o mylist.o
KERNEL ?= /lib/modules/4.15.0-76-generic/build
all:
	make -C $(KERNEL) M=$(PWD) modules
clean:
	make -C $(KERNEL) M=$(PWD) clean
