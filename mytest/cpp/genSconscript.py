#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
cwd = os.getcwd()

def find_source_files(dir_name):
    file_list = []
    for root, sub_dirs, files in os.walk(dir_name):
        for file in os.listdir(root):
            file_path = os.path.join(root, file)
            if os.path.isdir(file_path):
                pass
            else:
                if file_path.endswith(".c") or file_path.endswith(".cpp"):
                    file_list.append(file_path)
    return file_list

def generate_sconscript(dir_list, libs_flag = ""):
    for dir in dir_list:
        absDir = os.path.join(cwd, dir)
        if dir.startswith("cpp"):
            version = dir.replace("cpp", "c++")
        else:
            version = "c++11"
        print(absDir)
        os.chdir(absDir)

        f = open('SConscript', 'w')
        f.write('#1. Env setttings' + os.linesep)
        f.write('import os' + os.linesep)
        f.write('env=Environment(ENV=os.environ)' + os.linesep)
        f.write('env.Append(CXXFLAGS=\"--std=' + version + '\")' + os.linesep)
        f.write('#2. Targets' + os.linesep)
        file_list = find_source_files(absDir)
        file_list.sort()
        for file_path in file_list:
            basename = os.path.basename(file_path)
            p = os.path.splitext(basename)[0]
            if libs_flag == "":
                f.write("env.Program(target=\'" + p + "\', source=\'" + basename + "\')" + os.linesep)
            else:
                f.write("env.Program(target=\'" + p + "\', source=\'" + basename + libs_flag + os.linesep)
        f.close()

generate_sconscript(["cpp11", "cpp14", "cpp17"], "\', LIBS=[\'atomic\', \'pthread\'])")
generate_sconscript(["cpp98", "c99", "datastructure"])
