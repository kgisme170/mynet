#!/usr/bin/env python
# -*- coding: utf-8 -*-
from numpy import ones,array,arange,sin,pi,random
#from scipy import io as spio
from scipy import linalg,fftpack,optimize#,stats
import matplotlib.pyplot as plt

a=ones((3,3))
#spio.savemat('file.mat') ?cannot find
#spio.loadmat('file.mat')
arr=array([[1,2],[3,4]])
print linalg.det(arr)
print linalg.inv(arr)#??
print '---fft------'

timestep=0.02
period=5
timevec=arange(0,20,timestep)
sig=sin(2*pi/period*timestep+0.5*random.randn(timevec.size))
sigfft=fftpack.fft(sig)
print sigfft
#plt.plot(timevec,sig)
#plt.show()
print '---optimize---'
def f(x):
    return x**2+10*sin(x)
x=arange(-10,10,0.1)
#plt.plot(x,f(x))
#plt.show()
optimize.fmin_bfgs(f,0)
print '---stats---'
a=random.normal(size=1000)
#loc,std=stats.norm.fit(a)
#print loc,std
#print median(a)

#在mac环境下要使用matplotlib最好用virtualenv
#virtualenv /tmp/lpy
#source /tmp/lpy/bin/activate
#pip install matplotlib
