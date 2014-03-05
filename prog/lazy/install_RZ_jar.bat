net stop "Apache Tomcat"
o:
cd O:\LAZY\Src\LazyNodeServer\dist
copy /y LazyNodeServer.jar "C:\Tomcat 5.5\webapps\lazy\WEB-INF\lib\LazyNodeServer.jar"
net start "Apache Tomcat"
