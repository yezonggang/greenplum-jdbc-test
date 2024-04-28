#!/bin/sh
currentPath=$(cd `dirname $0`; pwd -P)

java -Xms512m \
     -Xmx512m \
     -XX:+UseG1GC \
     -XX:MaxMetaspaceSize=128m \
     -Xss512k \
     -XX:+PrintGCDetails \
     -XX:+PrintGCDateStamps \
     -XX:+PrintGCTimeStamps \
     -XX:+GCLogFileSize=10M \
     -XX:+HeapDumpOnOutOfMemoryError \
     -server \
     -Duser.dir=${currentPath} \
     -jar ${currentPath}/greenplum-jdbc-test.jar
