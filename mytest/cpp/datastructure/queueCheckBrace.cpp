#include <iostream>
using namespace std;
/*功能:
 *检查一个程序当中的括号是否匹配，单双引号中的忽略不计。单双引号也必须匹配
 */
char matchList[]={'{','}','[',']','(',')','\'','\"'};
bool inMatchList(char c) {
    for (size_t i = 0; i < sizeof(matchList) / sizeof(char); ++i) {
        if (c == matchList[i])return true;
    }
    return false;
}
struct stack {
    char buf[1024];
    size_t idx;

    stack() : idx(-1) {}

    void push(char c) {
        buf[++idx] = c;
    }

    void pop() {
        --idx;
    }

    char get() {
        return buf[idx];
    }

    bool matchBrace(char c) {
        return (c == '}' && get() == '{') ||
               (c == ']' && get() == '[') ||
               (c == ')' && get() == '(');
    }

    bool shouldPushBrace(char c) {
        return (c == '{' || c == '[' || c == '(');
    }

    bool shouldPopBrace(char c) {
        return (c == '}' || c == ']' || c == ')');
    }

    void opSingleQuote() {
        if (get() == '\'')pop();
        else push('\'');
    }

    void opDoubleQuote() {
        if (get() == '\"')pop();
        else push('\"');
    }

    bool isValidBrace(char c) {
        if (!inMatchList(c)) {
            //cout<<"不在match列表中\n";
            return false;
        }
        char s = get();
        if (s == '\'' || s == '\"') {//注释中
            return false;
        } else return true;
    }

    void op(char c) {
        if (!isValidBrace(c))return;
        if (c == '\'')opSingleQuote();
        else if (c == '\"')opDoubleQuote();
        else {
            if (shouldPushBrace(c)) {
                //cout<<"push(\'"<<c<<"\')"<<endl;
                push(c);
            } else if (shouldPopBrace(c)) {
                //cout<<"pop(\'"<<c<<"\')"<<endl;
                if (!matchBrace(c)) {
                    cout << "括号不匹配\n";
                    return;
                }
                pop();
            } else {
                cout << "编程错误\n";
                return;
            }
        }
    }
};
bool match(const char* a) {//把需要匹配的括号放到一个栈当中
    stack s;
    while (*a != '\0') {
        s.op(*a++);
    }
    return s.idx == -1;
}
int main() {
    cout << boolalpha;
    cout << match("{()}") << endl;
    cout << match("{ ( [ )}") << endl;
    cout << match("{ ( \'[\' )}") << endl;
    return 0;
}