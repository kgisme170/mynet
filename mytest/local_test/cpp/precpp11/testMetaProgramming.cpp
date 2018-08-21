#include<iostream>
using namespace std;
struct RandomAccessIterator{};
struct NormalIterator{};
struct Container1{//std::vector,deque,array等
    struct iterator{
        typedef RandomAccessIterator iterator_type;
        void operator+=(size_t len){}
    };
    iterator begin(){return iterator();}
};
struct Container2{//std::list,forward_list等
    struct iterator{
        typedef NormalIterator iterator_type;
        void operator++(){}
    };
    iterator begin(){return iterator();}
};
template<typename IteratorType>
IteratorType advance_impl(IteratorType it,size_t steps,RandomAccessIterator)
{
    it+=steps;
    return it;
}
template<typename IteratorType>
IteratorType advance_impl(IteratorType it,size_t steps,NormalIterator)
{
    for(size_t i=0;i<steps;++i)++it;
    return it;
}
template<typename IteratorType>
void advance(IteratorType it,size_t steps){
    advance_impl(it,steps,typename IteratorType::iterator_type());
}
int main(){
    Container1 c1;
    advance(c1.begin(),3);
    Container2 c2;
    advance(c2.begin(),3);
    return 0;
}
