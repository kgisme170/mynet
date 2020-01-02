#include"info.h"
void my::GetVersionInfoMap(std::map<std::string, std::string>& info)
{
    info["GIT_BRANCH"] = GIT_BRANCH;
    info["GIT_REVISION"] = GIT_REVISION;
    info["GIT_URL"] = GIT_URL;
}

std::string my::GetVersionInfo()
{
    try
    {
        std::map<std::string, std::string> versionInfo;
        GetVersionInfoMap(versionInfo);
        return "GetVersionInfo successful";
    } catch (...) {
    }

    return "{\"BUILD_NAME\":\"<Unknown>\",\"BUILD_TIME\":\"<Unknown>\",\"BUILD_GCC_VERSION\":\"<Unknown>\",\"BUILD_URL\":\"<Unknown>\",\"DEPEND_VERSION\":\"<Unknown>\",\"BUILD_TIME\":\"<Unknown>\",\"WORKER_GIT_REVISION\":\"<Unknown>\",\"WORKER_GIT_BRANCH\":\"<Unknown>\"}";
}
