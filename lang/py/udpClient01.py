#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from socket import *
from time import ctime
HOST='localhost' # HOST='#1'再测试一下，socket(AF_INET6)
PORT=21567
BUFSIZ=2014
ADDR=(HOST,PORT)

udpCliSock=socket(AF_INET, SOCK_DGRAM)
while True:
    data=raw_input('> ')
    if not data:
        break
    udpCliSock.sendto(data,ADDR)
    data, addr=udpCliSock.recvfrom(BUFSIZ)
    if not data:
        break
    print data
udpCliSock.close()