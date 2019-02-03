int main(){
    M obj;
    auto i=obj.GetValue();
    auto j=obj.GetRef();
    auto&k=obj.GetRef();

    ++i;++j;
    printf("%d\n",obj.m_i);//打印2
    ++k;
    printf("%d\n",obj.m_i);//打印3

    return 0;
}