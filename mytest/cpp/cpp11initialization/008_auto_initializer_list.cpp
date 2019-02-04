template<class T>
void f(T t){}
int main(){
    auto x={1,2,3};//或者用auto得到initializer_list类型。
    f(x);
    return 0;
}