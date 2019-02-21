#include <unistd.h>
#include <cstdio>
#include <float.h>
#include <string>
#include <vector>
#include <sstream>
#include <iostream>

#include <llvm/Support/ManagedStatic.h>
#include <llvm/Support/DynamicLibrary.h>
#include <llvm/Support/MemoryBuffer.h>
#include <llvm/Support/TargetSelect.h>
#include <llvm/Support/raw_ostream.h>
#include <llvm/Support/PrettyStackTrace.h>
#include <llvm/Bitcode/ReaderWriter.h>

#include <llvm/IR/DataLayout.h>
#include <llvm/IR/DerivedTypes.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/LegacyPassManager.h>
#include <llvm/IR/Verifier.h>

#include <llvm/Analysis/Passes.h>
#include <llvm/Analysis/TargetTransformInfo.h>

#include <llvm/Target/TargetMachine.h>

#include <llvm/Transforms/Scalar.h>
#include "llvm/Transforms/IPO.h"
#include <llvm/Transforms/IPO/PassManagerBuilder.h>
#include <llvm/Transforms/Utils/Cloning.h>

#include <llvm/ExecutionEngine/GenericValue.h>
#include <llvm/ExecutionEngine/MCJIT.h>
#include <llvm/ExecutionEngine/ExecutionEngine.h>
#include "llvm_gen.h"

#define Debug() cerr

using namespace std;
using namespace llvm;
