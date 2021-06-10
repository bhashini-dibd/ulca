FROM openjdk:8-jre-slim 
RUN groupadd -r appgroup
RUN useradd -r appuser -G appgroup
RUN mkdir /app
USER appuser
COPY build/ulca-api-0.0.1-SNAPSHOT.jar /app/ulca-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/testwebapp-0.1.0.jar"]

