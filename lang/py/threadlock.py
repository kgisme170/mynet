#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import thread
import threading
from time import sleep
import sys

def func():
    lock.acquire()
    print '--func'
    sleep(1)
    print '--after thread'
    lock.release()
lock=threading.Lock()
t=threading.Thread(target=func,args=())
t.start()
lock.acquire()
print "main start"
sleep(1)
lock.release()
print 'after main release'
t.join()
