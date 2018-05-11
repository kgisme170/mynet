#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from socket import *
from time import ctime
HOST=''
PORT=21567
BUFSIZ=1024
ADDR=(HOST,PORT)
udpServerSock=socket(AF_INET, SOCK_DGRAM)
udpServerSock.bind(ADDR)

while True:
    print 'Wait connection'
    data, addr=udpServerSock.recvfrom(BUFSIZ)
    print "Connected from:", addr

    udpServerSock.sendto('[%s] %s' % (ctime(), data), addr)
udpServerSock.close()