import threading
local=threading.local()
def get_msg():
    msg=local.msg
    print msg,",",threading.current_thread().name
def thread_func(msg):
    local.msg=msg
    get_msg()

t1=threading.Thread(target=thread_func,args=('A',),name="1st thread")
t2=threading.Thread(target=thread_func,args=('B',),name="2nd thread")
t1.start()
t2.start()
t1.join()
t2.join()