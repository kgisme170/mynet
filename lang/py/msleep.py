#!/usr/bin/env python
import thread
from time import sleep, ctime
def loop0():
    print "l0,", ctime()
    sleep(4)

def loop1():
    print "l1,", ctime()
    sleep(2)

thread.start_new_thread(loop0,())
thread.start_new_thread(loop1,())
sleep(6)

class he(object):
    def __new__(cls,*args,**kwargs):
        print 'he.__new__'
        return object.__new__(cls)
    def __str__(self):
        return 'he'
h = he()
print h
