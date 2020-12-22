FROM tomcat:9.0.36-jdk8-openjdk-slim
COPY build/libs/CheeseMilk-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
RUN mv /usr/local/tomcat/webapps/CheeseMilk-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]
