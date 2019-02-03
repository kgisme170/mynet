struct M{};
template<typename T>
void f(T&& t){
}
int main(){
    M obj;
    f(obj);
    f(M());
    return 0;
}