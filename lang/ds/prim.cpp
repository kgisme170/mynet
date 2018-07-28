#include <iostream>
#include <deque>
#include <limits.h>
#include <algorithm>
using namespace std;
struct edge{
    size_t v1;
    size_t v2;
};
/*功能: prim算法 采用邻接矩阵存储*/
int matrix[6][6]={
    {INT_MAX,6,1,5,INT_MAX,INT_MAX},
    {6,INT_MAX,5,INT_MAX,3,INT_MAX},
    {1,5,INT_MAX,5,6,4},
    {5,INT_MAX,5,INT_MAX,INT_MAX,2},
    {INT_MAX,3,6,INT_MAX,INT_MAX,6},
    {INT_MAX,INT_MAX,4,2,6,INT_MAX}
};
void prim(){
}
int main(){
    prim();
    return 0;
}
