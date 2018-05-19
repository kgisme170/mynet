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
    def myexception(self):
        print 1/0
    def __str__(self):
        return 'ok'

def get_context():
    return Context()

with Context() as ret:
    print ret
with get_context() as ret:
    print "context:"
    #ret.myexception()


class Item():
    def __init__(self,name):
        self._name=name
    def __repr__(self):
        return "Item's name is :" + self._name
    __str__=__repr__
print Item("Car")