#!/usr/bin/env python
# -*- coding: utf-8 -*- 
import logging
logging.basicConfig(level=logging.DEBUG,  
                    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',  
                    datefmt='%a, %d %b %Y %H:%M:%S',  
                    filemode='w')
logging.debug('debug')
logging.info('info')
logging.warn('warn')
logging.error('error')
#我从网上看到object这个基本类型的定义：为什么__new__这个基本类型的方法是@staticmethod,而继承类的__new__方法实现可以不加@staticmethod?
#class object:
#    @staticmethod
#    def __new__(cls, *more):
#        pass
class A(object):
    # @staticmethod python这里的*表示什么？C语言*表示指针，**表示两重指针
    def __new__(cls,*args,**kwargs):
        print 'A.__new__'
class B(object):
    def __new__(cls,*args,**kwargs):
        print 'B.__new__'
        return A.__new__(cls, *args, **kwargs)
    def __init__(self):
        print 'myis B'
b=B()

class Context(object):
    def __init__(self):
        print("my is init")
    def __enter__(self):
        print("my is enter")
    def __exit__(self, *args):
        print("my is exit")

with Context() as a:
    print(a)
print __name__

print u"这里"
import win32com.client as win32
from Tkinter import Tk
from tkMessageBox import showwarning
from time import sleep
warn=lambda app:showwarning(app, 'Exit?')
RANGE=range(3,8)
def excel():
    app='Excel'
    xl=win32.gencache.EnsureDispatch('%s.Application' % app)
    ss=xl.Workbooks.Add()
    sh=ss.ActiveSheet
    xl.Visible=True
    sleep(1)
    sh.Cells(1,1).Value='Python-to-%s Demo' % app
    sleep(1)
    for i in RANGE:
        sh.Cells(i,1).Value='Line %d' % i
        sleep(1)
        sh.Cells(i+2,i).Value="Th-th-th-that's all floks!"
        warn(app)
        ss.Close(False)
        xl.Application.Quit()
Tk().withdraw()
excel()

