#FROM python:3.7-slim
FROM anuvaadio/validate:4
COPY / /app
WORKDIR /app
RUN pip3 install -r requirements.txt
RUN apt update
RUN apt install ffmpeg
COPY start.sh /usr/bin/start.sh
RUN chmod +x /usr/bin/start.sh
CMD ["/usr/bin/start.sh"]
