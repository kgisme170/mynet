#include<stdio.h>
 const int constInt=2;
 int constFunction(){return 2;}
 int InAnotherFile();
 int add(int i,int j){return i+j;}
 int main(int argc, char*argv[]) {
     char buf1[constInt];
     buf1[1] = 9;
     char buf2[constFunction()];
     const int s = constFunction();
     char buf3[s];
     char buf4[InAnotherFile()];
     char buf5[add(3, argc)];
     printf("%lu,%lu,%lu,%lu\n", sizeof(buf2), sizeof(buf3), sizeof(buf4), sizeof(buf5));
     return 0;
 }
