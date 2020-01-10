#include "llvm_gen.h"
#define Debug() cerr

bool llvm_gen::init(const string& fileName) {
    if(!llvm_is_multithreaded()) {
	    printf("Not multi thread\n");
    }
    InitializeNativeTarget();
    InitializeNativeTargetAsmPrinter();
    InitializeNativeTargetAsmParser();

    ErrorOr<std::unique_ptr<MemoryBuffer>> buf = MemoryBuffer::getFile(fileName);
    Expected<std::unique_ptr<Module>> m = parseBitcodeFile(**buf, mContext);
    if (m) {
        printf("Fail to parse IR module\n");
        return false;
    }
    mModule.reset(m->get());
    mModule->setModuleIdentifier("myId");
    mEngineBuilder.reset(new EngineBuilder(move(mModule)));
    string cpuName = sys::getHostCPUName().str();
    mEngineBuilder->setMCPU(cpuName);
    mEngineBuilder->setEngineKind(EngineKind::JIT);
    mEngineBuilder->setOptLevel(CodeGenOpt::Aggressive);
    string sErr;
    mEngineBuilder->setErrorStr(&sErr);
    ExecutionEngine* mMCJit = mEngineBuilder->create();
    if (!mMCJit) {
        printf("Cannot create MCJit: %s\n", sErr.c_str());
        return false;
    }
    mVoidType = Type::getVoidTy(mContext);
    mPtrType = PointerType::get(mModule->getTypeByName("type01"), 0);

    mBitTrueValue = ConstantInt::get(mContext, APInt(1, true, true));
    mBitFalseValue = ConstantInt::get(mContext, APInt(1, false, true));

    mBoolTrueValue = ConstantInt::get(mContext, APInt(8, 1, true));
    mBoolFalseValue = ConstantInt::get(mContext, APInt(8, 0, true));

    mBoolNullValue = ConstantInt::get(mContext, APInt(8, -1, true));
    mInt64NullValue = ConstantInt::get(mContext, APInt(64, 0x8000000000000000, true));
    mDoubleNullValue = ConstantFP::get(mContext, APFloat(-DBL_MAX));
    mDatetimeNullValue = mInt64NullValue;
    return true;
}

int main(int argc, char* argv[]){
    llvm_gen gen;
    gen.init(argv[1]);
    return 0;
}
