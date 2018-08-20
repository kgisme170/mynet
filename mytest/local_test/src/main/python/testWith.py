class Context(object):
    def __init__(self):
        print("my is init")
    def __enter__(self):
        print("my is enter")
        return Context()
    def __exit__(self, type, value, trace):
        print("my is exit")
        print "type:", type
        print "value:", value
        print "trace:", trace
        return True # 如果返回False，异常会被重举，True，异常被终止
    def myexception(self):
        print 1/0
    def __str__(self):
        return 'ok'

def get_context():
    return Context()

with Context() as ret:
    print ret
with get_context() as ret:
   ret.myexception()
