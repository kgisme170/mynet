#pragma once
#include <stdint.h>
#include <set>
#include <string>
#include <vector>
#include <iostream>
#include <tr1/functional>
#include <tr1/unordered_set>
#include <llvm/IR/Constants.h>
#include <llvm/IR/DerivedTypes.h>
#include <llvm/IR/DIBuilder.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Instructions.h>
#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/Module.h>
#include <llvm/ExecutionEngine/GenericValue.h>
#include <llvm/ExecutionEngine/ExecutionEngine.h>

typedef std::tr1::function<void(void)> llmmCallback;

#include <map>
#include <string>
#include <sstream>
#include <iomanip>
#include <algorithm>

#include "llvm/IR/Instruction.h"
#include "llvm/IR/Module.h"
#include "llvm/IR/Function.h"
