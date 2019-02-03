struct M{
    int m_i;
    M():m_i(2){}
    int GetValue(){return m_i;}
};
int main(){
    M obj;
    auto i=obj.GetValue();//从返回值获取类型
    M             obj2;//如果obj的类型改变，这行需要改
    decltype(obj) obj3;//如果obj的类型改变，这行不用改
    auto j=obj.GetValue();
    return 0;
}