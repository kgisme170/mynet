#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
void fpe_handler(int signum) {
  printf("fpe\n");
  exit(1);
}

int main() {
  int b = 0;
  signal(SIGFPE, fpe_handler);
  printf("%d\n", 3 / b);
  return 0;
}