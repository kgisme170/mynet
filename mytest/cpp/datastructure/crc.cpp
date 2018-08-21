#include<stdio.h>
#include<stdlib.h>
#include<string.h>
//CRC算法
void inputBinary(const char* print, char* buf){
    bool loop = true;
    while(loop){
        printf("%s", print);
        scanf("%s", buf);
        size_t len = strlen(buf);
        bool ok = true;
        for(size_t i=0;i<len;++i){
            if(buf[i]!='1' && buf[i]!='0'){
                printf("buf[%u]=%c不符合条件\n", i, buf[i]);
                ok = false;
                break;
            }
        }
        loop = !ok;
    }
}
int binaryStringToInt(const char* msg, size_t len){
    if(msg[0]!='1'){
        printf("ERROR: 输入数据不是以1开头\n");
        return -1;
    }
    int s = 1;
    for(size_t i=1;i<len;++i){
        s<<=1;
        s+= msg[i]=='1'? 1:0;
    }
    return s;
}
void print_bin(int n){
    int l = sizeof(n)*8;//总位数。
    int i;
    for(i = l-1; i >= 0; i --){//略去高位0
        if(n&(1<<i)) break;
    }
    for(;i>=0; i --){
        printf("%d", (n&(1<<i)) != 0);
    }
}
int next(int t, size_t idx){
    //返回一个整数2进制表示的第idx位，从右往左数
    int result = t>>(idx-1);
    return result & 1;
}
int crc(int target, int gx, size_t lenT, size_t lenG){
    //lenT是模2除法的次数，lenG是取得被除数的长度
    int g_ = (gx>>(lenG-1))<<(lenG-1);//用于判断商是0还是1
    int result = target>>(lenT-1);
    for(size_t i=1;i<lenT;++i){
        result = result >= g_ ?
            result ^ gx : result;
        result<<=1;
        if(i<lenT - 1){
            result += next(target, lenT-i);
        }
    }
    return result;
}
int main(){
    //输入一个待发送信息，输入一个生成式
    char target[1024], gx[1024];
    size_t lenT, lenG;
    while(true){
        inputBinary("crc target=:", target);
        inputBinary("g(x)=", gx);

        lenT = strlen(target);
        lenG = strlen(gx);
        if(lenT <= 2){
            printf("ERROR: target 长度长度太小\n");
        }else if(lenG <= 2){
            printf("ERROR: g(x) 长度长度太小\n");
        }else if(lenT < lenG){
            printf("ERROR: target长度小于g(x)长度\n");
        }else{
            break;
        }
    }
    int t = binaryStringToInt(target, lenT);
    t<<=lenG - 1;
    int g = binaryStringToInt(gx, lenG);
    int r = crc(t, g, lenT, lenG);//1101011011,10011输出1110
    puts("crc 校验位=");
    print_bin(r);
    puts("\n");
    return 0;
}
