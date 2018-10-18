# coding=utf-8
from socket import *
import time

tcpSocket = socket(AF_INET, SOCK_STREAM)
# 重复使用绑定信息,不必等待2MSL时间
tcpSocket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)

address = ('', 9999)
tcpSocket.bind(address)
tcpSocket.listen(5)

count=0
while True:
    count+=1
    time.sleep(1)
    print('开启等待')
    newData, newAddr = tcpSocket.accept()
    print('%s客户端已经连接，准备处理数据' % newAddr[0])
    try:
        newData.send("abc ")
        newData.send("xyz ")
        newData.send("abc\n")
    finally:
        newData.close()
    if(count>=10):
        break

tcpSocket.close()s