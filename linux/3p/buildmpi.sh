#!/bin/bash
mpic++ mpi_init.cpp
mpic++ mpi_usage.cpp
mpiexec -n 2 ./a.out
