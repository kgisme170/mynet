#!/usr/bin/env python
# -*- coding: utf-8 -*-
import threading
import time


def f(tid):
    lock = threading.Lock()
    i = 100
    while --i:
        lock.acquire()
        print(i)
        lock.release()
        time.sleep(1)


t = threading.Thread(target=f, args=(3, ))
t.start()