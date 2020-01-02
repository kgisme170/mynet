#include<algorithm>
#include<limits>
#include<string>
#include"stdint.h"
#include"Pipes.hh"
#include"TemplateFactory.hh"
#include"StringUtils.hh"
using namespace std;
using namespace HadoopPipes;
using namespace HadoopUtils;
class wMapper:public Mapper {
public:
    wMapper(TaskContext &) {}

    void map(MapContext &context) {
        string line = context.getInputValue();
        vector <string> words = splitString(line, " ");
        for (size_t i = 0; i < words.size(); ++i) {
            context.emit(words[i], toString(i));
        }
    }
};
class wReducer:public Reducer {
public:
    wReducer(TaskContext &) {}

    void reduce(ReduceContext &context) {
        int count = 0;
        while (context.nextValue()) {
            count += toInt(context.getInputValue());
        }
        context.emit(context.getInputKey(), toString(count));
    }
};
int main() {
    return HadoopPipes::runTask(TemplateFactory<wMapper, wReducer>());
}