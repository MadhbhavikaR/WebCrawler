@ECHO OFF
TITLE WebCrawler Server
SET JAVA_HOME=C:\PROGRA~1\Java\JDK16~1.0_4
SET BIN=%CD%
CD ..
SET LOCAL_HOME=%CD%
CD %BIN%
SET PATH=%JAVA_HOME%\bin;%PATH%
SET CLASSPATH=%LOCAL_HOME%\libs\*;%LOCAL_HOME%\plugins\*
SET CONFIG_PATH=%LOCAL_HOME%\config
SET CONFIG_FILE=%CONFIG_PATH%\config.properties
SET LOG_CONFIG_FILE=%CONFIG_PATH%\logback.xml
ECHO classpath %CLASSPATH%
java -cp "%CLASSPATH%" -DwebCrawler.config.file=="%CONFIG_FILE%" -Dlogback.configurationFile="%LOG_CONFIG_FILE%" webcrawler.Start