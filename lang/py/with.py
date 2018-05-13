class Context(object):
    def __init__(self):
        print("my is init")
    def __enter__(self):
        print("my is enter")
    def __exit__(self, *args):
        print("my is exit")
    def __str__(self):
        print 'ok'

with Context() as a:
    print a