#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from socket import *
from time import ctime
HOST=''
PORT=21567
BUFSIZ=1024
ADDR=(HOST,PORT)

tcpServerSock=socket(AF_INET, SOCK_STREAM)
tcpServerSock.bind(ADDR)
tcpServerSock.listen(5)

while True:
    print 'Wait connection'
    tcpCliSock, addr=tcpServerSock.accept()
    print "......Connected from:", addr

    while True:
        data=tcpCliSock.recv(BUFSIZ)
        if not data:
            break
        tcpCliSock.send('[%s] %s' % (ctime(), data))
    tcpCliSock.close()
tcpServerSock.close()