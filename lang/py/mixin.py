class foobase:
    def hello(self):
        print 'base hello'

class foo:
    def __repr__(self):
        print 'my foo'
    def hello(self):
        print 'hello'

foo.__bases__+=(foobase,)
foo().hello()
h=getattr(foobase,"hello")
setattr(foo,"hello",h)
foo().hello()
