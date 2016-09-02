FROM openjdk:7

COPY . /lib/bud/src/
WORKDIR /lib/bud/src
RUN ./mvnw clean package -Djar.finalName=bud
RUN mv target/bud.jar /lib/bud

WORKDIR /lib/bud
RUN rm -rf src
RUN rm -rf /root/.m2

ENTRYPOINT ["java","-cp", "bud.jar", "me.predatorray.bud.lisp.BudRepl"]

