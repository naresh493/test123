FROM tomcat
MAINTAINER naresh

ADD QlikTestAdmin.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]