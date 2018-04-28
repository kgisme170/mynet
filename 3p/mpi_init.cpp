#include <iostream>
using namespace std;
#include "mpi.h"
int main(int argv,char* argc[]){
    MPI_Init(&argv,&argc);
    cout<<"hello world"<<endl;
    MPI_Finalize();
    return 0;
}
