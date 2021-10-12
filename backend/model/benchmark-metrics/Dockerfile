FROM python:3.7-slim
#FROM anuvaadio/validate:4
COPY / /app
WORKDIR /app
#COPY install-pkg.sh /usr/bin/install-pkg.sh
#RUN chmod +x /usr/bin/install-pkg.sh
#CMD ["/usr/bin/install-pkg.sh"]
#RUN echo uconv -V
RUN apt-get update
RUN apt-get -y install g++
RUN python3 -m pip install pybind11
RUN pip3 install -r requirements.txt
RUN python3 -m nltk.downloader 'wordnet'
COPY start.sh /usr/bin/start.sh
RUN chmod +x /usr/bin/start.sh
CMD ["/usr/bin/start.sh"]
