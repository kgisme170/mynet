class A(object):
    def f(self):
        print 'A f'
class B(A):
    def __init__(self):
        print "B init"
class C(A):
    def f(self):
        print 'C f'

class D(B,C):
    def g(self):
        f()
d=D()
d.g()