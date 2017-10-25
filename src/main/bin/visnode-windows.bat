@echo off
setlocal EnableDelayedExpansion
set classpath=
for %%i in (lib\*.jar) do (
   set classpath=!classpath!;%%i
)
javaw -cp "%classpath%" visnode.application.VISNode
endlocal
