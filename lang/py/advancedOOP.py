from types import MethodType
class My(object):
    def f1(self):
        print 'f1'

def f2(self):
    print 'f2'

m=My()
m.f2=MethodType(f2,m,My)
m.f2()

