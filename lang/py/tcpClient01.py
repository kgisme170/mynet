#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from socket import *
from time import ctime
HOST='localhost' # HOST='#1'再测试一下，socket(AF_INET6)
PORT=21567
BUFSIZ=1024
ADDR=(HOST,PORT)

tcpCliSock=socket(AF_INET, SOCK_STREAM)
tcpCliSock.connect(ADDR)

while True:
    data=raw_input('> ')
    if not data:
        break
    tcpCliSock.send(data)
    data=tcpCliSock.recv(BUFSIZ)
    if not data:
        break;
    print data
tcpCliSock.close()