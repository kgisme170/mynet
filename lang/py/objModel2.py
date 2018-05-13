class A:
    def __init__(self):
        print 'this is A'

    def save(self):
        print 'come from A'

class B(A):
    def __init__(self):
        print 'this is B'

class C(A):
    def __init__(self):
        print 'this is C'
    def save(self):
        print 'come from C'

class D(B,C):
    def __init__(self):
        print 'this is D'

d1=D()
d1.save()  #结果为'come from A