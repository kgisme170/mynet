import socket
address = ('127.0.0.1', 7000)
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(address)
s.send('hi\n')
s.close()