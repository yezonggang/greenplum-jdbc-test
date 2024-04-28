#!/bin/bash

dockerid=`docker ps -a --no-trunc| grep greenplum-jdbc-test |grep -v CONTAINER| awk '{print $1}'`

if [[ -n "${dockerid}" ]];then
  docker stop ${dockerid} && docker rm ${dockerid}
fi

docker run -d \
  --name=greenplum-jdbc-test \
  -v /etc/localtime:/etc/localtime \
  -v /etc/sysctl.conf:/etc/sysctl.conf \
  -v /var/log/mpp/mpp.log:/var/log/mpp/mpp.log \
  -v /opt/greenplum-jdbc-test.jar:/opt/mpp/greenplum-jdbc-test.jar \
  --restart=always \
  --cpus 1 \
  --c 1024 \
  -p 9999:9999 \
  -e greenplum.user=jdbctest \
  -e greenplum.passwd=admin@123 \
  -e greenplum.db=jdbctest \
  -e greenplum.url=jdbc:postgresql://19.25.36.19:5434 \
  -e pool.type=druid \
  -e pool.test=false \
  greenplum-jdbc-test:latest