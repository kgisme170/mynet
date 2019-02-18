#include<map>
extern const char* const GIT_BRANCH = "master";
extern const char* const GIT_REVISION = "Version";
extern const char* const GIT_URL = "";

namespace my{
    void GetVersionInfoMap(std::map<std::string, std::string>& info);
    std::string GetVersionInfo();
}
