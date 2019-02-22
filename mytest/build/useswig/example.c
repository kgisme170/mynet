#include <time.h>
double My_variable = 3.0;
 
int fact(int n) {
    if (n <= 1) return 1;
    else return n*fact(n-1);
}
 
int my_mod(int x, int y) {
    return (x%y);
}
 
char *get_time() {
    time_t ltime;
    time(&ltime);
    return ctime(&ltime);
}
/*
 *
% tclsh
% load ./example.so example
% puts $My_variable
3.0
% fact 5
120
% my_mod 7 3
1
% get_time
Sun Feb 11 23:01:07 1996

%
*/
