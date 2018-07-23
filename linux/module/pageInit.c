#define NR_PGT 0x4
#define PGD_BASE (unsigned int*)0x1000
#define PAGE_OFFSET (unsigned int)0x2000

#define PTE_PRE 0x01/*page present*/
#define PTE_RW 0x02 /*Page Readable/Writable */
#define PTE_USR 0x04 /*User Privilege Level */

int main(){
    int pages = NR_PGT; // 系统初始化4个页表
    unsigned int page_offset = PAGE_OFFSET;
    unsigned int *pgd = PGD_BASE; //页目录表位于内存的第二个页框
    unsigned int phy_add = 0x0000; // 在物理地址的最低端建立页机制所需的表格

    unsigned int* pgt_entry=(unsigned int*)0x2000;
    while(pages--){
        *pgd++ = page_offset|PTE_USR|PTE_RW|PTE_USR;
        page_offset += 0x1000;//4k
    }
    pgd = PGD_BASE;
    while(phy_add<0x1000000){//4M
        *pgt_entry++ = phy_add|PTE_USR|PTE_RW|PTE_USR;
        phy_add += 0x1000;
    }
    __asm__ __volatile__("movl %0,k %%cr3;"
                         "movl %%cr0, %%eax;"
                         "orl $0x80000000, %%eax;"
                         "movl %%eax, %%cr0;"::"r"(pgd):"memory","%eax");
    return 0;
}
