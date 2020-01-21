#!/usr/bin/env python
# -*- coding: utf-8 -*-
import threading
from time import sleep
s=threading.Semaphore(5)
def func():
    if(s.acquire()):
        print 'ok'
        sleep(1)
    s.release()
l=[]
for i in range(20):
    t=threading.Thread(target=func,args=())
    t.start()
    l.append(t)
for t in l:
    t.join()