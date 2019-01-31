#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
cwd = os.getcwd()

def find_source_files(dir_name):
    file_list = []
    print("find " + dir_name)
    for root, sub_dirs, files in os.walk(dir_name):
        for file in os.listdir(root):
            file_path = os.path.join(root, file)
            if os.path.isdir(file_path):
                pass
            else:
                if file_path.endswith(".c") or file_path.endswith(".cpp"):
                    file_list.append(file_path)
    return file_list

for dir in ["cpp11", "cpp14", "cpp17"]:
    absDir = os.path.join(cwd, dir)
    version = dir.replace("cpp", "c++")
    print(absDir)
    os.chdir(absDir)

    f = open('SConscript', 'w')
    f.write('#1. Env setttings' + os.linesep)
    f.write('import os' + os.linesep)
    f.write('env=Environment(ENV=os.environ)' + os.linesep)
    f.write('env.Append(CXXFLAGS=\"--std=' + version + '\"' + os.linesep)
    f.write('#2. Targets' + os.linesep)
    file_list = find_source_files(absDir)
    file_list.sort()
    for file_path in file_list:
        basename = os.path.basename(file_path)
        p = os.path.splitext(basename)[0]
        LIBS = "\', LIBS=[\'atomic\', \'pthread\'])"
        cmd = "env.Program(target=\'" + p + "\', source=\'" + basename +  + os.linesep
        f.write(cmd)
    f.close()