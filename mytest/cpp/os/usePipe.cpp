 #include <stdlib.h>
 #include <stdio.h>
 #include <unistd.h>

 int main(){
   int pipefd[2];
   pipe(pipefd);
   int& readfd = pipefd[0];
   int& writefd = pipefd[1];

   pid_t pid = fork();

   if(pid == 0){ //child
     dup2(writefd, 1);  // 1 is STDOUT_FILENO -- cat already has input -- needs output
     close(readfd);
     execlp("cat","cat","tmp.txt", NULL);
     perror("execlp() failed in child");

   }else{ //father
     dup2(readfd, 0); // 0 is STDIN_FILENO -- because sort needs input!
     close(writefd);
     execlp("sort","sort", NULL);
     perror("execlp() failed in parent");
   }
   return 0;
 }