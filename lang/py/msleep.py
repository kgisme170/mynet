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
#.net await
# subprocess
# concurrent.futures














