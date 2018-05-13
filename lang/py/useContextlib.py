from contextlib import contextmanager
@contextmanager
def myfile():
    print 'Begin'
    f=open("d:\\tmp.txt","w")
    yield f
    f.close()
    print 'End'

with myfile() as f:
    print type(f)
    f.write("ok")

@contextmanager
def closing(thing):
    try:
        yield thing
    finally:
        thing.close()

from urllib import urlopen
with closing(urlopen('https://www.python.org')) as page:
    for line in page:
        print(line)