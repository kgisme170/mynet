# \!/bin/bash

wget -q https://packages.microsoft.com/config/ubuntu/18.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb \
&& dpkg -i packages-microsoft-prod.deb \
&& add-apt-repository universe \
&& apt-get update \
&& apt-get install -y apt-transport-https dotnet-sdk-3.1 aspnetcore-runtime-3.1 dotnet-runtime-3.1
