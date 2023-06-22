FROM maven:3.9.2-amazoncorretto-20

COPY target/pinnacle-bot-*-jar-with-dependencies.jar /opt/pinnacle-bot.jar

ENV SYS_PROPS="-Xmx256m --add-modules=jdk.incubator.concurrent"

ENV DB_USER=""
ENV DB_PASS=""
ENV DB_URL=""

EXPOSE 3000

ENTRYPOINT java -jar ${SYS_PROPS} -Ddb-url=${DB_URL} -Ddb-username=${DB_USER} -Ddb-password=${DB_PASS} /opt/pinnacle-bot.jar
