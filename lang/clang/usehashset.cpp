#include<unordered_set>
using namespace std;
struct node{
     size_t value;
     node* next;
     node():value(-1),next(NULL){}
};
struct myhash{
     size_t operator()(const node& n)const{
         return (size_t)n.value;
     }
};
size_t h(const node& n){
     return (size_t)n.value;
}
int main(){
     unordered_set<node, myhash> s1;
     auto f=[](const node& n){
         return (size_t)n.value;
     };
     unordered_set<node, decltype(f)> s2(f);
     //unordered_set<node, [](const node& e){return n.value;}> s;
     return 0;
}