#!/usr/bin/env python
# -*- coding: utf-8 -*-  
from SocketServer import (TCPServer as TCP, StreamRequestHandler as SRH)
from time import ctime
HOST=''
PORT=21571
ADDR=(HOST,PORT)

class MyRequestHandler(SRH):
    def handler(self):
        print '...connected from :', self.client_address
        self.wfile.write('[%s] %s' % (ctime(), self.rfile.readline()))
tcpServer=TCP(ADDR,MyRequestHandler)
tcpServer.serve_forever()