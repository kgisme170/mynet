#!/usr/bin/env python
# -*- coding: utf-8 -*-
class A: # 老的对象模型，不从object继承，多继承是深度优先搜索父类方法
    def __init__(self):
        print 'this is A'
    def f(self):
        print 'come from A'

class B(A):
    def __init__(self):
        print 'this is B'

class C(A):
    def __init__(self):
        print 'this is C'
    def f(self):
        print 'come from C'

class D(B,C):
    def __init__(self):
        print 'this is D'
    pass

d1=D()
d1.f()  #结果为'come from A