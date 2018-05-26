#!/usr/bin/env python
# -*- coding: utf-8 -*-
def decorate1(func):
    def inner():
        print "Before"
        func()
    return inner

@decorate1
def f1():
    print 'f1'
f1()

def decorate2(func):
    def inner(args):
        print "Before"
        func(args)
    return inner

@decorate2
def f2(msg):
    print msg
f2('abc')

def before():
    print "before"
def after():
    print "after"
def filter(before_func,after_func):
    def outer(main_func):
        def inner(args):
            before_func()
            main_func(args)
            after_func()
        return inner
    return outer
@filter(before, after)
def f3(msg):
    print msg

f3('xyz')
print '---'

def decorate3(func):
    def inner(args):
        print 'd3'
        func(args)
    return inner
def decorate4(func):
    def inner(args):
        print 'd4'
        func(args)
    return inner
@decorate3
@decorate4
def f4(msg):
    print msg
f4('hh')