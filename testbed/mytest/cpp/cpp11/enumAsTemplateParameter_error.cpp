using namespace std;
enum class A:int {
    u = 1,
    v = 2
};
template<A value>
int next() {
    return value + 1;
}
int main() {
    next<A::u>();
    return 0;
}
