#!/usr/bin/env python
# -*- coding: utf-8 -*-
import socket
address = ('127.0.0.1', 9999)
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(address)
data = s.recv(1024)
print data
s.close()