#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
import sys

l = []


def producer():
    for i in range(10):
        l.append(i)
        yield i
        ++i
        time.sleep(1)


def consume():
    p = producer()
    while True:
        try:
            p.next()
            while len(l):
                print l.pop()
        except:
            print "except"
            sys.exit(1)

consume()
