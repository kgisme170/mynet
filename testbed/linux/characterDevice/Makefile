obj-m += hello.o
all:
	make -C /lib/modules/4.15.0-74-generic/build M=$(PWD) modules
clean:
	make -C /lib/modules/4.15.0-74-generic/build M=$(PWD) clean
