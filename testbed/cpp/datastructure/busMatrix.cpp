#include <iostream>
#include <vector>
using namespace std;

class MySolution {
    vector <vector<int>> &passengerNum;
    int path[1024]; // 表示经过某条路径上各个节点的人数
    size_t row; // 矩阵的行数
    size_t col; // 矩阵的列数
    size_t maxDepth; // 路径上的节点数
    int tempMax; // 用于不断更新的最大乘客数量

    // 求当前路径上的乘客总数
    void sumAndUpdate() {
        int total = 0;
        for (size_t i = 0; i < maxDepth; ++i) {
            total += path[i];
        }
        cout << "sumAndUpdate(), total=" << total << endl;
        // 更新最大值
        if (total > tempMax) {
            tempMax = total;
        }
    }

    void stepAndCalc(size_t x/*横坐标*/, size_t y/*纵坐标*/, size_t nodeNum) {
        cout << "x=" << x << ",y=" << y << ",nodeNum=" << nodeNum << endl;
        if (nodeNum == maxDepth - 1) {
            sumAndUpdate();
        } else {
            if (x < row - 1) {
                path[nodeNum + 1] = passengerNum[x + 1][y];
                stepAndCalc(x + 1, y, nodeNum + 1);
            }
            if (y < col - 1) {
                path[nodeNum + 1] = passengerNum[x][y + 1];
                stepAndCalc(x, y + 1, nodeNum + 1);
            }
        }
    }

public:
    MySolution(vector <vector<int>> *_passengerNum)
            : passengerNum(*_passengerNum), row(passengerNum.size()), col(passengerNum[0].size()),
              maxDepth(row + col - 1), tempMax(0) {
        path[0] = passengerNum[0][0];
        cout << "row=" << row << ",col=" << col << ",maxDepth=" << maxDepth << endl;
    }

    int getMax() {
        stepAndCalc(0, 0, 0);
        return tempMax;
    }
};

int solution(vector<vector<int> >& passengerNum) {
    MySolution sol(&passengerNum);
    int maxPassengers = sol.getMax();
    return maxPassengers;
}
int main() {
    vector <vector<int>> v(2);
    vector<int> &v1 = v[0];
    v1.push_back(5);
    v1.push_back(2);
    v1.push_back(4);
    vector<int> &v2 = v[1];
    v2.push_back(1);
    v2.push_back(6);
    v2.push_back(0);
    cout << solution(v) << endl;
    return 0;
}