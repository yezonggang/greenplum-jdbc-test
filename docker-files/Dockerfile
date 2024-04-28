FROM blade.h3c.com:443/helm/dbs/openjdk-base:v2.0

RUN mkdir -p /opt/mpp
ADD ../target/greenplum-jdbc-test.jar /opt/mpp/
ADD docker-entrypoint.sh /opt/mpp/
RUN chmod +x /opt/mpp/*

WORKDIR /opt/mpp
ENTRYPOINT ["/opt/mpp/docker-entrypoint.sh"]