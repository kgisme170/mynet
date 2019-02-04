template<class Head,class ...Tail>
Head sum(Head h,Tail&&...value){
    Head sum=h;
    (int[]){(sum+=value,0),...};
    return sum;
}
int main(){
    return sum(1,2,3.0);
}