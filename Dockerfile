FROM maven:3.6-jdk-11

COPY pom.xml /addresses_manager/
COPY src /addresses_manager/src/

WORKDIR /addresses_manager/

RUN mvn clean package -Pproduction -DskipTests

ENV JAVA_OPTS="--spring.profiles.active=production"

ENTRYPOINT java -jar target/api-1.0.jar $JAVA_OPTS
