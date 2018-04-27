import SimpleHTTPServer
import SocketServer
PORT=8081
Handler=SimpleHTTPServer.SimpleHTTPRequestHandler
print type(Handler)

httpd=SocketServer.TCPServer(("",PORT),Handler)
httpd.serve_forever()
