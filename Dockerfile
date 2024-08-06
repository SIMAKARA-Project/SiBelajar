FROM maven:3.9.8-amazoncorretto-17-debian AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17.0.12-al2023
WORKDIR /app
COPY --from=build /app/target/learning-management-system-0.0.1-SNAPSHOT.jar learning-management-system-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","learning-management-system-0.0.1-SNAPSHOT.jar"]