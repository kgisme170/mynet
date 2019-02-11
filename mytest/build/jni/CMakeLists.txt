cmake_minimum_required (VERSION 2.8) 
execute_process(COMMAND javac cpp2java.java)
execute_process(COMMAND javah -jni cpp2java)
execute_process(COMMAND scons -u)
