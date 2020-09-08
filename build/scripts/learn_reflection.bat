@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  learn_reflection startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and LEARN_REFLECTION_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\learn_reflection-1.0.1.jar;%APP_HOME%\lib\commons-math3-3.6.1.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.11.1.jar;%APP_HOME%\lib\kafka-avro-serializer-5.3.0.jar;%APP_HOME%\lib\kafka-schema-registry-client-5.3.0.jar;%APP_HOME%\lib\avro-1.9.2.jar;%APP_HOME%\lib\kafka-clients-5.3.0-ccs.jar;%APP_HOME%\lib\jackson-databind-2.11.1.jar;%APP_HOME%\lib\jackson-annotations-2.11.1.jar;%APP_HOME%\lib\jackson-core-2.11.1.jar;%APP_HOME%\lib\commons-compress-1.19.jar;%APP_HOME%\lib\common-config-5.3.0.jar;%APP_HOME%\lib\common-utils-5.3.0.jar;%APP_HOME%\lib\zkclient-0.10.jar;%APP_HOME%\lib\zookeeper-3.4.14.jar;%APP_HOME%\lib\slf4j-api-1.7.26.jar;%APP_HOME%\lib\snappy-java-1.1.7.3.jar;%APP_HOME%\lib\zstd-jni-1.4.0-1.jar;%APP_HOME%\lib\lz4-java-1.6.0.jar;%APP_HOME%\lib\spotbugs-annotations-3.1.9.jar;%APP_HOME%\lib\jline-0.9.94.jar;%APP_HOME%\lib\audience-annotations-0.5.0.jar;%APP_HOME%\lib\netty-3.10.6.Final.jar;%APP_HOME%\lib\jsr305-3.0.2.jar

@rem Execute learn_reflection
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %LEARN_REFLECTION_OPTS%  -classpath "%CLASSPATH%" ${group}.package1.HelloWorld %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable LEARN_REFLECTION_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%LEARN_REFLECTION_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
