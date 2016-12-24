FROM tomcat:7.0.73-jre7
MAINTAINER naresh

ADD target/QlikTestAdmin.war /usr/local/tomcat/webapps/
Expose 8080
CMD ["catalina.sh", "run"]
