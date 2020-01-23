#!/usr/bin/env python
class Fib(object):
    def __init__(self, max):
        super(Fib, self).__init__()
        self.max = max

    def __iter__(self):
        self.a = 0
        self.b = 1
        return self

    def __next__(self):
        fib = self.a
        if fib > self.max:
            raise StopIteration
        self.a, self.b = self.b, self.a + self.b
        return fib

    def next(self):
        return self.__next__()


def main():
    fib = Fib(100)
    for i in fib:
        print(i)

if __name__ == '__main__':
    main()
