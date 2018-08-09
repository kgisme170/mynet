#include<iostream>
#include<fstream>
#include<vector>
#include<array>
#include<string>
#include<sstream>
#include<cstdarg>
using namespace std;
struct mat{
    size_t row;
    size_t col;
    vector<vector<size_t> > data;
};
struct banker{
    size_t rType;
    size_t nProc;
    typedef vector<long> vl;
    typedef vector<vl> vArray;
    vArray allocation;
    vArray need;
    vl available;
    void inputData(vArray& a, istream& is){
        string line;
        for(size_t i=0;i<nProc;++i){
            getline(is, line);
            istringstream input(line);
            vector<long> v;
            for (std::array<char, 100> a; input.getline(&a[0], 4, ' '); ) {
                cout<<&a[0]<<',';
                long l = stol(&a[0]);
                v.push_back(l);
            }
            a.push_back(v);
            cout<<'\n';
        }
    }
    banker(istream& is){
        string line;
        getline(is, line);
        rType = stoi(line);
        getline(is, line);
        nProc = stoi(line);
        cout<<rType<<" types,"<<nProc<<" processes\n";
        cout<<"Allocation:\n";
        inputData(allocation, is);
        cout<<"Need:\n";
        inputData(need, is);
        cout<<"Available:\n";
        getline(is, line);
        istringstream input(line);
        for (std::array<char, 100> a; input.getline(&a[0], 4, ' '); ) {
            cout<<&a[0]<<',';
            long l = stol(&a[0]);
            available.push_back(l);
        }
        cout<<'\n';
    }
    bool isProcValid(size_t idx){
        vl& a = allocation[idx];
        vl& n = need[idx];
        for(size_t r = 0;r<rType;++r){
            if(a[r]+n[r]>available[r])return false;
        }
        return true;
    }
    void endProc(size_t idx){
        vl& a = allocation[idx];
        for(size_t r = 0;r<rType;++r){
            available[r] += a[r];
            a[r] = 0;
        }
    }
    size_t checkSafeImpl(){
        vector<bool> finish(nProc, false);
        size_t count = 0;
        while(true){
            bool found = false;
            for(size_t n=0;n<nProc;++n){
                cout<<"for\n";
                if(!finish[n] && isProcValid(n)){
                    endProc(n);
                    finish[n]=true;
                    found = true;
                    ++count;
                    cout<<"Stop:"<<n<<",count="<<count<<endl;
                    if(count==nProc)break;
                }
            }
            if(found==false || count==nProc)break;
            cout<<"found="<<found<<",count="<<count<<",end\n";
        }
        cout<<"count="<<count<<endl;
        return count;
    }
    bool checkSafe(){
        vArray _al = allocation;
        vArray _ne = need;
        vl _av = available;
        size_t count = checkSafeImpl();

        allocation = _al;
        need = _ne;
        available = _av;
        return count == nProc;
    }
    bool checkSafe(size_t idx,...){
        vArray _al = allocation;
        vArray _ne = need;
        vl _av = available;

        va_list args;
        va_start(args, idx);
        vl& a = allocation[idx];
        vl& n = need[idx];
        for (size_t i = 0; i < rType; ++i) {
            size_t result = va_arg(args, size_t);
            cout<<"result="<<result<<endl;
            a[i] += result;
            n[i] -= result;
            available[i] -= result;
        }
        va_end(args);

        size_t count = checkSafeImpl();

        allocation = _al;
        need = _ne;
        available = _av;
        return count == nProc;
    }
};
int main(){
    fstream f("banker.txt", ios::in);
    banker b(f);
    f.close();
    cout<<boolalpha;
    cout<<b.checkSafe()<<endl;
    cout<<"============\n";
    cout<<b.checkSafe(1, 3, 2, 0, 1)<<endl;
    return 0;
}
