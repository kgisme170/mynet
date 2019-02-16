#include<zstd.h>
#include<stdio.h>
int main() {
    ZSTD_CCtx *ctx = ZSTD_createCCtx();
    char src[] = "abbcccddddfffffgggggghhhhhhh";
    char dst[128];
    size_t len = ZSTD_compressCCtx(ctx,
                                   dst,
                                   sizeof(dst),
                                   src,
                                   sizeof(src),
                                   5);
    printf("%s\n", dst);
    size_t s = ZSTD_freeCCtx(ctx);
    return 0;
}