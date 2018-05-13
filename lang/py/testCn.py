#!/usr/bin/env python
# -*- coding: utf-8 -*- 
#with open("E:\\问题.txt") as f:
#    print f.read()

import contextlib
class you(object):
#    def __new__(cls,*args,**kwargs):
#        print 'you.__new__'
    def __init__(self):
        print '__init__'
    def __str__(self):
        return 'you'
y=you()
print y

class he(object):
    def __new__(cls,*args,**kwargs):
        print 'he.__new__'
    def __str__(self):
        return 'he'
h = he()
print h