@echo off
setlocal
if "%1" == "a" (
    echo "1st param is a"
) else (
    echo %1
)
echo %%

set argC=0
for %%x in (%*) do Set /A argC+=1
echo %argC%

call :myprint
goto Exit

:myprint
echo "haha"
exit /b 0

endlocal

print "end"
:exit