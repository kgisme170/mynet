### 曲线积分(linear integral)

#### 计算曲线下的面积(对弧长的曲线积分)
对于一维的情况f(x)下面的面积就是x定义域上的定积分，有一个积分的范围。对于2维的情况，我们想要计算(x,y)平面上f(x,y)在某个定义曲线对应的曲面的面积。这个就是对弧长的曲线积分。

这个曲面实际上是垂直于(x,y)平面的一个竖着的墙，可以理解为平面上，定积分的平面区域被放到了3维空间，求一个曲面的面积。这个曲面的上边沿就是f(x,y)，下边沿就是(x,y)平面。

假设x,y都是t的函数,x=g(t),y=h(t)，然后对于弧长的积分可以写成 <img src="img/linearIntegral01.gif">

举个具体的例子，f(x,y)=xy, 积分曲线C是x=cost, y=sint, t=[0, PI/2]，是在第一象限的一个半圆。
<img src="img/linearIntegral02.gif">，再设u=sin(t)，原式=<img src="img/linearIntegral03.gif">

再举一个例子，在第一象限的围线积分，半圆+x轴+y轴组成的区域，其实就是3个墙的面积:

<img src="img/linearIntegral04.gif">

在三段线上分别用x(t),y(t)来计算。在园弧上:

<img src="img/linearIntegral05.gif"> = PI/2，然后在y轴上，x=0, y=2-t, t从0到2之间。dx/dt=0, dy/dt=-1，

<img src="img/linearIntegral06.gif">，在x轴上，x=t, y=0, t从0到2，可以算出来积分=2. 3部分的值加起来就是结果。

同理，三维空间的函数f(x,y,z)对于面积的曲面积分，原理类似，只是增加了一个维度而已，还是写成t的函数来求解。