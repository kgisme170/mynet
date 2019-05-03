@echo off
setlocal enableDelayedExpansion

REM The space before the = is interpreted as part of the name,
REM and the space after it (as well as the quotation marks)
REM are interpreted as part of the value.

set Location = "bob"
echo %Location %
set Name=abc
echo %Name%
set "myname = john"
echo %myname %
goto :exit

set Name=cd
echo Name=%Name%
if "%Name%"=="mm" (
  echo case1
) else (
  echo case2
  set Name=ll_value
  echo delay expansion, Name=!Name!
)
REM setlocal is like {} code block, to setup local variables
setlocal
set Name=ef
echo after setlocal, Name=%Name%
endlocal
echo end setlocal, Name=%Name%

:exit