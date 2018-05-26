#!/usr/bin/env python
# -*- coding: utf-8 -*-
from datetime import datetime

class log(object):
    def __init__(self, logfile='./out.log'):
        print 'init'
        self.logfile = logfile

    def __call__(self, func):
        def wrapped_func(*args, **kwargs):                     
            time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            log_str = time+' operator:{0[0]} did[{0[1]}operation'.format(args)           
            with open(self.logfile, 'a') as file:
                file.write(log_str + '\n')
            return func(*args, **kwargs)
        return wrapped_func

@log()
def myfunc(name,age):
    print('name:{0},age:{1}'.format(name,age))

if __name__ == '__main__':
    myfunc('abc', 'query')
    myfunc('root', 'add')
    myfunc('xyz', 'modify')
