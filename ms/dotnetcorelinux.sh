# \!/bin/bash

wget -q https://packages.microsoft.com/config/ubuntu/18.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb \
&& dpkg -i packages-microsoft-prod.deb \
&& add-apt-repository universe \
&& apt-get update \
&& apt-get install apt-transport-https\
&& apt-get update \
&& apt-get install dotnet-sdk-3.1 \
&& apt-get install aspnetcore-runtime-3.1 \
&& apt-get install dotnet-runtime-3.1