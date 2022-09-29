FROM openjdk:11.0.4-jre-slim
RUN groupadd -r appgroup
RUN useradd -r appuser -G appgroup
RUN mkdir /app
USER appuser
COPY target/ulca-dataset-api.jar /app/ulca-dataset-api.jar
ENTRYPOINT ["java","-jar","/app/ulca-dataset-api.jar"]

