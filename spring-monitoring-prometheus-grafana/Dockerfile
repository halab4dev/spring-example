FROM openjdk:8
VOLUME /tmp
ADD target/spring-monitoring-prometheus-grafana-0.0.1-SNAPSHOT.jar spring-monitoring-prometheus-grafana-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-prometheus-grafana-0.0.1-SNAPSHOT.jar"]
