#include<memory>
#include<vector>
using namespace std;
int main() {
  typedef unique_ptr<int> ui;
  vector <ui> vui;
  auto p = ui(new int(5));
  //vui.push_back(p);//不能通过编译，拷贝语义，unique_ptr已经将其标记为delete
  vui.push_back(move(p));//移动构造, OK，没有资源的重复申请/释放
  vui.push_back(ui(new int(6)));//就地构造OK
}