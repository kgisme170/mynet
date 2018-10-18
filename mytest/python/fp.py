#!/usr/bin/env python
# -*- coding: utf-8 -*-
import functools
l=map(lambda x:x*x,range(1,20))
print l
def add(x,y):
    return x+y
print reduce(add,l)

print reduce(lambda x,y:x+y,l)
f=lambda x,y:x+y
print reduce(f,l)

print filter(lambda x:x%2==1,[1,2,3,4,5])

def cmp_ignore_case(s1, s2):
    u1 = s1.upper()
    u2 = s2.upper()
    if u1 < u2:
        return -1
    if u1 > u2:
        return 1
    return 0
print sorted(['bob', 'about', 'Zoo', 'Credit'], cmp_ignore_case)

def generator():
    fs=[]
    for i in range(1,4):
        def f(j):
            def g():
                return j*j
            return g
        fs.append(f(i)) # returns a g which use 'j' as function parameter
    return fs
f1,f2,f3=generator()
print f1(),f2(),f3()

def log(func):
    @functools.wraps(func)
    def f(*args, **kw):
        print 'call %s():' % func.__name__
        return func(*args, **kw)
    return f
@log
def now():
    print "now"

now()
print "name=", now.__name__
def log2(msg):
    def impl(func):
        @functools.wraps(func)
        def f(*args, **kw):
            print '%s call %s():' % (msg, func.__name__)
            return func(*args, **kw)
        return f
    return impl

@log2('Param version')
def now2():
    print "now2"
now2()
print "name=", now2.__name__