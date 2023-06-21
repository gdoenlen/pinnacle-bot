FROM maven:3.9.2-amazoncorretto-20

COPY target/pinnacle-bot-1.0-SNAPSHOT-jar-with-dependencies.jar pinnacle-bot-1.0.jar

ENV JAR_ARGS=""

ENV SYS_PROPS="-Xmx256m --add-modules=jdk.incubator.concurrent"

EXPOSE 3000

ENTRYPOINT java -jar ${SYS_PROPS} pinnacle-bot-1.0.jar ${JAR_ARGS}

