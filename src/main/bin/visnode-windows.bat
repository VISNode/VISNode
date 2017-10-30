@echo off
setlocal EnableDelayedExpansion
set classpath=
for %%i in (lib\*.jar) do (
   set classpath=!classpath!;%%i
)
start javaw -cp "%classpath%" visnode.application.VISNode
endlocal
