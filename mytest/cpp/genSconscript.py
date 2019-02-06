#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
sys.path.append("..")
from findFiles import *

#generate_sconscript("c99", "--std=c99")
generate_sconscript("cpp11", "--std=c++11")
generate_sconscript("cpp11concurrent", "--std=c++11", "['atomic', 'pthread']")
generate_sconscript("cpp11initializer", "--std=c++11")
generate_sconscript("cpp11share", "--std=c++11")
generate_sconscript("cpp11variadic", "--std=c++11")
generate_sconscript("datastructure", "--std=c++11")

generate_sconscript("cpp14", "--std=c++14")
generate_sconscript("cpp17", "--std=c++17")

generate_sconscript("cpp98", "--std=c++98")
generate_sconscript("cpp98metaprogramming", "--std=c++98")
generate_sconscript("cpp98namelookup", "--std=c++98")
generate_sconscript("cpp98virtualstatic", "--std=c++98")

