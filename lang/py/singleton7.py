#!/usr/bin/env python
# -*- coding: utf-8 -*-
class s1(object):
    instance=None
    def __init__(self):
        raise SyntaxError('Singleton s1 cannnot __init__')
    @staticmethod
    def get_instance():
        if s1.instance==None:
            s1.instance=object.__new__(s1)
        return s1.instance
    def p(self):
        print 's1'

s1.get_instance().p()
print '-------------'
class s2(object):
    instance=None
    def __init__(self):
        raise SyntaxError('Singleton s2 cannnot __init__')
    @classmethod
    def get_instance(cls):
        if cls.instance==None:
            cls.instance=object.__new__(cls)
        return cls.instance
    def p(self):
        print 's2'
s2.get_instance().p()
print '-------------'
#类属性方法
class s3(object):
    instance=None
    def __new__(cls,*args,**kw):
        if not cls.instance:
            #cls.instance=object.__new__(cls)
            cls.instance=super(s3,cls).__new__(cls,*args,**kw)
        return cls.instance
    def p(self):
        print 's3'
s3().p()
print '-------------'
def f4(cls):#how does class decorator work?
    i = {}
    def myinstance():
        if cls not in i:
            i[cls] = cls()
        return i[cls]
    return myinstance
@f4
class m:
    pass
a = m()
b = m()
c = m()

print(id(a))
print(id(b))
print(id(c))
print '-------------'
class s4(type):
    def __init__(cls, name, bases, dct):
        super(s4, cls).__init__(name, bases, dct)
        cls.instance = None
    def __call__(cls, *args):#why is this called?
        if cls.instance is None:
            cls.instance = super(s4, cls).__call__(*args)
        return cls.instance
class MyClass(object):
    __metaclass__ = s4
a = MyClass()
b = MyClass()
c = MyClass()
print(id(a))
print(id(b))
print(id(c))
print(a is b)
print(a is c)
print '-------------'
class Singleton(object):
    def foo(self):
        print('foo')

    def __call__(self):
        return self

Singleton = Singleton()
Singleton.foo()
a = Singleton()
b = Singleton()
print(id(a))#why identical
print(id(b))
