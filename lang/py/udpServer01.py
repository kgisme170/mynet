#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from socket import *
from time import ctime
HOST=''
PORT=21567
BUFSIZ=2014
ADDR=(HOST,PORT)
udpServerSock=socket(AF_INET, SOCK_DGRAM)
udpServerSock.bind(ADDR)

while True:
    print '等待连接'
    data, addr=udpServerSock.recvfrom(BUFSIZ)
    print "......连接来自于:", addr

    udpServerSock.sendto('[%s] %s' % (ctime(), data))
udpServerSock.close()