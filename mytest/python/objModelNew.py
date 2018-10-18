#!/usr/bin/env python
# -*- coding: utf-8 -*-
class A(object): # 从object继承的新的对象模型，继承类广度优先搜索父类的方法
    def f(self):
        print 'A f'
class B(A):
    def __init__(self):
        print "B init"
class C(A):
    def f(self):
        print 'C f'

class D(B,C):
    def __init__(self):
        print "D init"
    def g(self):
        super(D,self).f()
d=D()
d.f() # C f
d.g() # C f