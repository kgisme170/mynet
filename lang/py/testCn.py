#!/usr/bin/env python
# -*- coding: utf-8 -*- 


print __name__

print u"这里"

def mycom():


#with open("E:\\问题.txt") as f:
#    print f.read()

import contextlib
class you(object):
    def __new__(cls,*args,**kwargs):
        print 'you.__new__'
    def __init__(self):
        print '__init__'
    def __enter__(self):
        print '__enter__'
    def __exit__(self, exc_type, exc_value, traceback):
        print '__exit__'

y=you()