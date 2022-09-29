FROM openjdk:11.0.4-jre-slim
RUN groupadd -r appgroup
RUN useradd -r appuser -G appgroup
RUN mkdir /app
USER appuser
COPY target/ulca-dataset-download.jar /app/ulca-dataset-download.jar
ENTRYPOINT ["java","-jar","/app/ulca-dataset-download.jar"]

