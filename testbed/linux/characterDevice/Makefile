obj-m += hello.o
all:
	make -C $(shell ls -d /lib/modules/*-generic/build) M=$(PWD) modules
clean:
	make -C $(shell ls -d /lib/modules/*-generic/build) M=$(PWD) clean
