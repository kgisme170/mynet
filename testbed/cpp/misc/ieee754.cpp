#include <stdio.h>
#include <stdlib.h>
void printFloat(float f) {
	int* p = (int*)&f;
	int ftoi = *p;
	int sign = ftoi>>31;
	printf("\n%f, sign=%c", f, sign==0 ? '+' : '-');
	int point = ((ftoi&0x7fffffff) >> 23) - 127;
    printf(", point = %d\n", point);

	char buf[20];
	ltoa(ftoi, buf, 2);
	printf("%032s\n", buf);
}
int main() {
	printFloat(1);
	printFloat(-1);
	printFloat(1.5);
	printFloat(1.25);
	printFloat(1.125);
	printFloat(1.255);
	printFloat(2.5);
	printFloat(5);
	printFloat(0.125);
	return 0;
}
/*
1: sign = 0, point = 0
00111111100000000000000000000000

-1: sign = -1, point = 0
10111111100000000000000000000000

1.5: sign = 0, point = 0
00111111110000000000000000000000

1.25: sign = 0, point = 0
00111111101000000000000000000000

1.125: sign = 0, point = 0
00111111100100000000000000000000

1.255: sign = 0, point = 0
00111111101000001010001111010111

2.5: sign = 0, point = 1
01000000001000000000000000000000

5: sign = 0, point = 2
01000000101000000000000000000000

0.125: sign = 0, point = -3
00111110000000000000000000000000
*/
