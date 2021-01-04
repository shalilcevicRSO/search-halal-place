FROM adoptopenjdk:14-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/search-halal-place-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "search-halal-place-api-1.0.0-SNAPSHOT.jar"]