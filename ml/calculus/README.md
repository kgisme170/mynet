### 有约束条件的优化问题: 从梯度到拉格朗日乘数法

拉格朗日乘数法(Lagrange mutiplier)是基于这样的一个场景: 求一个n元函数的最大值，给出n-1个约束条件。如果n=2, 就是求f(x,y)的最大值，约束条件是g(x,y)=0。

一个最简单的例子来自可汗学院(Khan Academy) 视频地址 https://www.youtube.com/watch?v=BSKtQcLQLWU 

#### 例子摘录如下:

有一个钢铁厂，每个工人工时的成本20元，总共使用h个工时； 钢铁原料s每吨2,000元。现在有20,000元的预算投入生产，问如何使得总的产出R(Revenue)最大:
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20R%3D100%20*%20h%5E%7B2/3%7D%20*%20s%5E%7B1/3%7D">
约束条件是
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%2020h&plus;2000s%5Cleq%2020000">

求解的思想:

#### 1. 约束条件
所谓小于等于20000元，我们显然知道是等于20000元时，可；以得到最大的产出。如果画出s-h的坐标系，约束条件就是20h+2000s=20000，这是一条负斜率的直线L。接下来就是最精髓的部分:

假设R取固定值k(满足条件R=k)，那么R就是s-h平面上面的一条曲线，画出来类似log曲线。对于不同的k，这些曲线形成一个曲线族，我称为Curves。如果在Curves里面有一条曲线C，它和L有且只有一个交点p，那么这个交点就是我们要求的最优解。

为什么? 如果有多个交点，说面曲线C可以继续向右上方移动，R可以取更大的k。如果没有交点，说明k是一个无法达到的值。因此如果有最优解，那么一定有且只有一个交点。这个思路其实是把约束条件看成是常量，待求曲线看成新的约束条件。

#### 2. 求解
OK, 现在我们知道L和C相交于p，那么p有什么特点？因为L是直线，和C相交于一点，立刻知道L是C的切线，p点处，C的法线方向就是L的垂线方向。用微积分的话说，在p点，C的梯度平行于L的梯度: 这个正比例关系用下面的式子表示:
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20grad%28Cp%29%20%3D%20n%20*%20grad%28Lp%29">

这里n是一个比例系数，书中常用希腊字母lambda表示，为了输入方便我就用英文字母n了。grad代表求梯度的函数。那么我们知道梯度是一个矢量:
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20grad%28Cp%29%3D%5Cbegin%7Bbmatrix%7D%20%5Cfrac%7B%5Cpartial%20C%7D%7B%5Cpartial%20s%7D%5C%5C%20%5Cfrac%7B%5Cpartial%20C%7D%7B%5Cpartial%20h%7D%20%5Cend%7Bbmatrix%7D%20%3D%20%5Cbegin%7Bbmatrix%7D%20100/3*h%5E%7B2/3%7D*s%5E%7B-2/3%7D%5C%5C%20200/3*h%5E%7B-1/3%7D*s%5E%7B2/3%7D%20%5Cend%7Bbmatrix%7D">
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20grad%28Lp%29%3D%5Cbegin%7Bbmatrix%7D%20%5Cfrac%7B%5Cpartial%20L%7D%7B%5Cpartial%20s%7D%5C%5C%20%5Cfrac%7B%5Cpartial%20L%7D%7B%5Cpartial%20h%7D%20%5Cend%7Bbmatrix%7D%20%3D%20%5Cbegin%7Bbmatrix%7D%202000%5C%5C%2020%20%5Cend%7Bbmatrix%7D">

对上面三个式子联立，可以消除n，然后得到h和s之间的关系: h=200s，再带入到约束条件里面我们得到:
<img src="https://latex.codecogs.com/gif.latex?%5Cbg_white%20h%3D10/3%2C%20s%3D2000/s">