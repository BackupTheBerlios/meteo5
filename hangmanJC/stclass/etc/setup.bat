@echo OFF
rem
rem   etc\setup.bat
rem
rem   setup the windows environment variables to use STclass
rem

set ver=<version>
set jver=<javaversion>

echo Setting up STclass-java-%ver% environment...

rem -------------------- please customize! --------------

rem --- The STclass install dir

rem set STCLASS_HOME="C:\Program Files"\STclass-java-%ver%
set STCLASS_HOME=H:\SCoT\STclass-java-%ver%

rem --- The Python install dir

rem set PYTHON_HOME="C:\Program Files\Python2.1.2"
set PYTHON_HOME=E:\Python22

rem --- The JDK install dir

set JAVA_HOME="C:\Program Files\%jver%"
rem set JAVA_HOME=E:\jdk1.3.1_02

rem ------------------------------------------------------

PATH=%PYTHON_HOME%;%JAVA_HOME%\bin;%PATH%

rem --- check STCLASS_HOME

if not exist %STCLASS_HOME%\bin\javacst (
  echo Error! %STCLASS_HOME%\bin\javacst not found!
  echo        Please correct the STCLASS_HOME variable in this setup.bat script!
  goto :EOF
) else echo STCLASS_HOME = %STCLASS_HOME%

rem --- find python

call :which python
if X%WHICH%==X (
  echo Error! python interpreter not found!
  echo        Please correct the PYTHONPATH variable in the setup.bat script!
) else echo Found python: %WHICH%

rem --- find python version

python -c "import sys; print sys.version" > pyver.tmp
for /F "tokens=1 delims= " %%v in (pyver.tmp) do set PYVER=%%v
for /F "tokens=1 delims=." %%v in (pyver.tmp) do set PYNV=%%v
del pyver.tmp
if %PYNV% LSS 2 (
  echo Error! python 2.x or more is required!
  echo Please install a Python 2.x first!
) else echo       version is OK: %PYVER%

rem --- find javac
rem     and assume java is in the same dir as javac

echo JAVA_HOME = %JAVA_HOME%

call :which javac
if X%WHICH%==X (
  echo Error! javac not found!
  echo        Please correct the JAVA_HOME variable in the setup.bat script!
)

rem --- CLASSPATH

rem set CLASSPATH=%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%lib\jdpa.jar;%JAVA_HOME%\lib\dt.jar
set CLASSPATH=.
set libst=%STCLASS_HOME%\lib\java2ST

PATH=%STCLASS_HOME%\bin;%PATH% 

java -version

set st=%libst%\STclass.jar
if exist %st% (
  set CLASSPATH=%st%;%CLASSPATH%
  echo Found STclass library: %st%
)
	
set st=%libst%\iDoclet.jar
if exist %st% (
  set CLASSPATH=%CLASSPATH%;%st%
  echo Found Contracts documentation with iDoclet: %st%
)

echo CLASSPATH = %CLASSPATH%

goto :EOF

rem ------------- which subroutine

:which
set WHICH=
for %%A IN (.\;%PATH%) do if exist %%A\%1.bat  set WHICH=%%A\%1.bat
for %%A in (.\;%PATH%) do if exist %%A\%1.exe  set WHICH=%%A\%1.exe
for %%A in (.\;%PATH%) do if exist %%A\%1      set WHICH=%%A\%1
goto :EOF

rem ---------- end of setup.bat


