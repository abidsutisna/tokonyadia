FROM openjdk:17-jdk-alpine
ADD target/*.jar tokonyadia.jar
CMD ["java","-jar","/tokonyadia.jar"]
