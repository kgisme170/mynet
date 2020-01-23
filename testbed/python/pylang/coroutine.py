#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
def consumer():
    r=''
    while True:
        n=yield r
        if not n:
            return
        print 'Consumer gets ',n
        time.sleep(1)
        r='ok'

def producer(c):
    c.next()
    n=0
    while n<5:
        n=n+1
        print 'Producer=',n
        r=c.send(n)
        print 'Consumer returns ',r
    c.close()
c=consumer()
producer(c)