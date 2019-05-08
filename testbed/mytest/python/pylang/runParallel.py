#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import os
import sys
import threading
threads = []
def execCmd(cmd):
    try:
        print "命令%s开始运行%s" % (cmd,datetime.datetime.now())
        os.system(cmd)
        print "命令%s结束运行%s" % (cmd,datetime.datetime.now())
    except Exception, e:
        print '%s\t 运行失败,失败原因\r\n%s' % (cmd,e)
for i in range(5):
    th=threading.Thread(target=execCmd ,args=("sleep 1",))
    th.start()
    threads.append(th)
for t in threads:
    t.join()
# 注意这个程序在linux上，1s后瞬间结束。而在mac上，线程逐个结束，也就是mac的线程会被当前进程的sleep状态卡住
