#!/usr/bin/env python
# -*- coding: utf-8 -*-
from multiprocessing import Process
import time
def task(msg):
    print 'hello, %s' % msg
    time.sleep(1)

if __name__ == '__main__':
    p = Process(target=task, args=('world',))
    p.start()
    if p.is_alive():
        print 'Process: %s is running' % p.pid
    p.join()