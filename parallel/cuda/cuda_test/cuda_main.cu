#include "cuda_runtime.h"  
#include "cublas_v2.h"  

#include <time.h>  
#include <iostream>  

using namespace std;

int const M = 5;
int const N = 10;

int main()
{
	cublasStatus_t status;
	float *h_A = (float*)malloc(N*M * sizeof(float));
	float *h_B = (float*)malloc(N*M * sizeof(float));
	float *h_C = (float*)malloc(M*M * sizeof(float));
	for (int i = 0; i < N*M; i++) {
		h_A[i] = (float)(rand() % 10 + 1);
		h_B[i] = (float)(rand() % 10 + 1);
	}
	for (int i = 0; i < N*M; i++) {
		cout << h_A[i] << " ";
		if ((i + 1) % N == 0) cout << endl;
	}
	cout << endl;
	for (int i = 0; i < N*M; i++) {
		cout << h_B[i] << " ";
		if ((i + 1) % M == 0) cout << endl;
	}
	cout << endl;
	cublasHandle_t handle;
	status = cublasCreate(&handle);
	if (status != CUBLAS_STATUS_SUCCESS)
	{
		if (status == CUBLAS_STATUS_NOT_INITIALIZED) {
			cout << "CUBLAS 对象实例化出错" << endl;
		}
		getchar();
		return EXIT_FAILURE;
	}
	float *d_A, *d_B, *d_C;
	cudaMalloc((void**)&d_A, N*M * sizeof(float));
	cudaMalloc((void**)&d_B, N*M * sizeof(float));
	cudaMalloc((void**)&d_C, M*M * sizeof(float));
	cublasSetVector(N*M, sizeof(float),	h_A, 1, d_A, 1);
	cublasSetVector(N*M, sizeof(float),	h_B, 1,	d_B, 1);
	float a = 1; float b = 0;
	cublasSgemm(
		handle,    // blas 库对象   
		CUBLAS_OP_T,    // 矩阵 A 属性参数  
		CUBLAS_OP_T,    // 矩阵 B 属性参数  
		M,    // A, C 的行数   
		M,    // B, C 的列数  
		N,    // A 的列数和 B 的行数  
		&a,    // 运算式的 α 值  
		d_A,    // A 在显存中的地址  
		N,    // lda  
		d_B,    // B 在显存中的地址  
		M,    // ldb  
		&b,    // 运算式的 β 值  
		d_C,    // C 在显存中的地址(结果矩阵)  
		M    // ldc  
	);
	cublasGetVector(M*M, sizeof(float), d_C, 1,	h_C, 1);
	cout << "(A*B)T: " << endl;
	for (int i = 0; i < M*M; i++) {
		cout << h_C[i] << " ";
		if ((i + 1) % M == 0) cout << endl;
	}
	free(h_A);
	free(h_B);
	free(h_C);
	cudaFree(d_A);
	cudaFree(d_B);
	cudaFree(d_C);

	cublasDestroy(handle);
	return 0;
}
