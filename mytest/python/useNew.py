# -*- coding: utf-8 -*- 
#网上看到object这个基本类型的定义__new__这个基本类型的方法是@staticmethod
#class object:
#    @staticmethod
#    def __new__(cls, *more):
#        pass
class A(object):
    def __new__(cls,*args,**kwargs):
        print 'A.__new__'
class B(object):
    def __new__(cls,*args,**kwargs):
        print 'B.__new__'
        return A.__new__(cls, *args, **kwargs)
    def __init__(self):
        print 'myis B'
b=B()