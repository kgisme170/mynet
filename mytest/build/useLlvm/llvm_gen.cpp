#include "llvm_gen.h"
#define Debug() cerr

bool llvm_gen::init(const string& fileName) {
    if(!llvm_is_multithreaded()) {
	printf("Not multi thread\n");
    }
    InitializeNativeTarget();
    InitializeNativeTargetAsmPrinter();
    InitializeNativeTargetAsmParser();

    LLVMContext mContext;
    ErrorOr<std::unique_ptr<MemoryBuffer>> buf = MemoryBuffer::getFile(fileName);
    Expected<std::unique_ptr<Module>> m = parseBitcodeFile(**buf, mContext);
    if (m) {
	printf("Fail to parse IR module\n");
	return false;
    }
    unique_ptr<Module> mModule(m->get());
    mModule->setModuleIdentifier("myId");
    unique_ptr<EngineBuilder> mEngineBuilder(new EngineBuilder(move(mModule)));
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
}

int main(int argc, char* argv[]){
    llvm_gen gen;
    gen.init(argv[1]);
    return 0;
}
