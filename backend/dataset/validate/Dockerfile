FROM python:3.7-slim
COPY / /app
WORKDIR /app
COPY install-pkg.sh /usr/bin/install-pkg.sh
RUN chmod +x /usr/bin/install-pkg.sh
CMD ["/usr/bin/install-pkg.sh"]
RUN pip3 install -r requirements.txt
RUN pip3 install --no-binary=:pyicu: pyicu
COPY start.sh /usr/bin/start.sh
RUN chmod +x /usr/bin/start.sh
CMD ["/usr/bin/start.sh"]