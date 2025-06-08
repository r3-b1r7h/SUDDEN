#JAVA_HOME=/opt/jdk1.6.0/bin 
set JAVA_HOME=/usr/lib/jvm/java
set JRE_HOME=/usr/lib/jvm/java/jre
set CATALINA_HOME=/opt/tomcat

mvn -Dmaven.test.skip=true clean install

rm /opt/tomcat/webapps/sudden.war
cp ./target/sudden.war /opt/tomcat/webapps
ls /opt/tomcat/webapps/

CURR=`pwd`
cd /opt/tomcat/bin
./catalina.sh jpda start
cd $CURR
