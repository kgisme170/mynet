准备目录
Robocopy d:\CosmosSamples d:\VcRoot /MIR

运行
d:\ScopeSDK\scope.exe run -i d:\test.script -INPUT_PATH d:\vcroot -OUTPUT_PATH d:\vcroot -workingRoot d:\ScopeTemp -RESOURCE_PATH d:\VcRoot

d:\ScopeSDK\scope.exe run -i D:\git\mynet\testbed\mytest\ms\TrillApp\ScopeApp1\bin\Debug\Scope3.script -INPUT_PATH D:\git\mynet\testbed\mytest\ms\TrillApp\ScopeApp1 -OUTPUT_PATH d:\vcroot -workingRoot d:\ScopeTemp -RESOURCE_PATH d:\VcRoot -UserDebugStream d:\ScopeTemp

只编译
d:\scopesdk\scope.exe compile –i test.script <Add the other parameters here>

MAP?

ss的输出如何支持udt?
