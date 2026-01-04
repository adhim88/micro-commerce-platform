FROM amazoncorretto:17
WORKDIR /user-service-app
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-app.jar
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=dev
CMD ["java", "-jar", "user-service-app.jar"]