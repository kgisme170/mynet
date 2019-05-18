### 什么是调和函数?

一句话简单理解: 调和函数就是函数的值处处等于周围邻域的值取平均。这就好比，最和谐的社会是，每个都都发现，自己的收入等于周围人的收入的平均值，每个人都觉得自己貌似中产，社会一片和谐。

当然，这样的理解是OK的，那么怎么求解这样的函数? 直观的一个解就是，恒等于某个常数的函数(0阶函数)，就像人人收入平均当然调和了。那么更高维的情况? 考虑一维的情况，

<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20f%28x%29%3Dax&plus;b">

这条直线上每个点x的f(x)都是邻域点的平均值。一阶的情况是这样。那么对于

<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20f%28x%29%3Dx%5E2">

是否可行? 答案是不行。因为f的二阶导数除了x=0点以外都是非0，因此变化率不是常量，只有0点是"调和"的。

那么对于2阶的情况，会有更多的解存在。例如

<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20f%28x%2Cy%29%3De%5Ex%20sin%28y%29">

求这个函数的拉普拉斯算子:

<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20%5CDelta%20f%28x%2Cy%29%3D%20%5Cbigtriangledown%20.%20%5Cbigtriangledown%20e%5Ex%20sin%28y%29%20%3D%20%5Cfrac%20%7B%5Cpartial%20%5E2f%7D%7B%5Cpartial%20x%5E2%7D%20&plus;%20%5Cfrac%20%7B%5Cpartial%20%5E2f%7D%7B%5Cpartial%20y%5E2%7D">

简单计算可知上式恒等于0，虽然这个函数的图形及其的复杂和陡峭。注意拉普拉斯算子的求法，梯度函数是个矢量，div函数是一个偏微分算子点乘梯度矢量，计算结果是一个标量(scalar)。