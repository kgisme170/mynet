#!/usr/bin/env python
# -*- coding: utf-8 -*-
import threading
from time import sleep
count=0
def func():
    global count
    temp=count+1
    count=temp
l=[]
for i in range(100):
    t=threading.Thread(target=func,args=())
    t.start()
    l.append(t)
for t in l:
    t.join()
print count

count1=1
def func1():
    global count1
    temp=count1+1
    sleep(0.001)
    count1=temp
l1=[]
for i in range(100):
    t=threading.Thread(target=func1,args=())
    t.start()
    l1.append(t)
for t in l1:
    t.join()
print count1