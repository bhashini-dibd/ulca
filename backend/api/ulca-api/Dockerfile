FROM openjdk:8-jre-slim 
RUN groupadd -r appgroup
RUN useradd -r appuser -G appgroup
RUN mkdir /app
USER appuser
COPY target/ulca-api.jar /app/ulca-api.jar
ENTRYPOINT ["java","-jar","/app/ulca-api.jar"]

