#!/bin/sh
set -eu

PORT="${PORT:-8080}"

# Tomcat 10.1's default HTTP connector listens on 8080.
# Render provides its public service port through $PORT.
sed -i "s/port=\"8080\"/port=\"${PORT}\"/" /usr/local/tomcat/conf/server.xml

exec /usr/local/tomcat/bin/catalina.sh run
