#FROM python:3.7-slim
FROM anuvaadio/validate:4
COPY / /app
WORKDIR /app
#COPY install-pkg.sh /usr/bin/install-pkg.sh
#RUN chmod +x /usr/bin/install-pkg.sh
#CMD ["/usr/bin/install-pkg.sh"]
#RUN echo uconv -V
# RUN apt-get update -y && apt-get install -y ffmpeg
RUN pip3 install -r requirements.txt
RUN polyglot download transliteration2.hi
COPY start.sh /usr/bin/start.sh
RUN chmod +x /usr/bin/start.sh
CMD ["/usr/bin/start.sh"]