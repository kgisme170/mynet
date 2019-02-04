#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
sys.path.append("..")
from findFiles import *

generate_sconscript("multiplex/linux", "--std=c++11", "['pthread']")
generate_sconscript("multiplex/posix", "--std=c++11", "['pthread']")
generate_sconscript("multiplex/pthread", "--std=c++11", "['pthread']")

