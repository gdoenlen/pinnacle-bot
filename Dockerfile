FROM maven:3.9.2-amazoncorretto-20

COPY target/pinnacle-bot-1.0-SNAPSHOT-jar-with-dependencies.jar pinnacle-bot-1.0.jar

ENV SYS_PROPS="-Xmx256m --add-modules=jdk.incubator.concurrent"

ENV DB_USER=""
ENV DB_PASS=""

EXPOSE 3000

ENTRYPOINT java -jar ${SYS_PROPS} -Ddb-username=${DB_USER} -Ddb-password=${DB_PASS} pinnacle-bot-1.0.jar
