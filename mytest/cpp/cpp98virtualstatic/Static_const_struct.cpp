struct POD{
    int i;
    int m;
};
struct Static_struct{
    const static POD pop={1,2};//编译错误
};
int main(){
    return 0;
}