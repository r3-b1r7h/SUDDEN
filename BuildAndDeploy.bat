set oldpath=path
set path=C:\Programme\Java\jdk1.6.0_13\bin;C:\Programme\SlikSvn\bin;D:\apache-maven-2.1.0\bin;%path%
set CATALINA_HOME=D:\apache-tomcat-6.0.18

set JAVA_HOME=C:\Programme\Java\jdk1.6.0_13\

cd SUDDEN

call D:\apache-tomcat-6.0.18\bin\shutdown.bat

svn update

call mvn -Dmaven.test.skip=true clean install 
del "D:\apache-tomcat-6.0.18\webapps\sudden"
copy "D:\SUDDEN\target\sudden.war" "D:\apache-tomcat-6.0.18\webapps"
dir "D:\apache-tomcat-6.0.18\webapps"

set JAVA_OPTS=-Xmx1024
call D:\apache-tomcat-6.0.18\bin\startup.bat 

set path=oldpath