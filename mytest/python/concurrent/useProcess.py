#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
import random
from multiprocessing import Process
from multiprocessing import JoinableQueue
q = JoinableQueue()


def consumer():
    while True:
        ret = q.get();
        time.sleep(random.randint(1, 3))
        print("消费" + ret);
        q.task_done()


def producer():
    for i in range(10):
        time.sleep(random.randint(1, 3))
        q.put("食物 %d", i)
        print("Produce No %d", i)
    q.join()

p1 = Process(target=consumer);
c1 = Process(target=producer);
p1.daemon = True
c1.daemon = True
p1.start()
c1.start()
p1.join()
