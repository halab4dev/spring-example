# Monitoring Spring Boot application with Prometheus and Grafana
![spring-prometheus-grafana](images/spring-prometheus-grafana.png)

## Spring boot application

##### Dependencies
In `pom.xml` file, add these 2 dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
        <version>1.3.0</version>
    </dependency>
</dependencies>
```
- Spring boot actuator automatically create endpoints that can provide metrics of application such as heap memory used, cpu load, GC information ...
- Micrometer registry prometheus will format these information in prometheus way

##### Enable endpoints
By default, Spring Boot Actuator only expose 2 endpoints: `/actuator/health`  and `/actuator/health`, so we need to enable other endpoint in configuration file:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: prometheus,health,info
```

Now we can get metrics:
```shell
curl 'http://localhost:8080/actuator/health'

{"status":"UP"}
```

```shell
curl 'http://localhost:8080/actuator/prometheus'

...
# HELP process_uptime_seconds The uptime of the Java virtual machine
# TYPE process_uptime_seconds gauge
process_uptime_seconds 278.817
# HELP jvm_memory_max_bytes The maximum amount of memory in bytes that can be used for memory management
# TYPE jvm_memory_max_bytes gauge
jvm_memory_max_bytes{area="heap",id="PS Survivor Space",} 1.048576E7
jvm_memory_max_bytes{area="heap",id="PS Old Gen",} 2.78396928E9
jvm_memory_max_bytes{area="heap",id="PS Eden Space",} 1.370488832E9
jvm_memory_max_bytes{area="nonheap",id="Metaspace",} -1.0
jvm_memory_max_bytes{area="nonheap",id="Code Cache",} 2.5165824E8
jvm_memory_max_bytes{area="nonheap",id="Compressed Class Space",} 1.073741824E9
# HELP jvm_gc_pause_seconds Time spent in GC pause
# TYPE jvm_gc_pause_seconds summary
jvm_gc_pause_seconds_count{action="end of major GC",cause="Metadata GC Threshold",} 1.0
jvm_gc_pause_seconds_sum{action="end of major GC",cause="Metadata GC Threshold",} 0.093
jvm_gc_pause_seconds_count{action="end of minor GC",cause="Metadata GC Threshold",} 1.0
jvm_gc_pause_seconds_sum{action="end of minor GC",cause="Metadata GC Threshold",} 0.014
# HELP system_load_average_1m The sum of the number of runnable entities queued to available processors and the number of runnable entities running on the available processors averaged over a period of time
# TYPE system_load_average_1m gauge
system_load_average_1m 1.34
...

```

## Prometheus
##### Job configuration
In configuration file of Prometheus, define a job to get metric from Spring boot application:
```yaml
scrape_configs:
  - job_name: 'spring-boot-metrics'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 15s
    static_configs:
      - targets: ['[spring-application-host]:8080']
```
This job will call to `'/actuator/prometheus'` endpoint to get metrics each 15 seconds

## Run all with docker
##### Create image for spring boot application
Dockerfile
```dockerfile
FROM openjdk:8
VOLUME /tmp
ADD target/spring-prometheus-grafana-0.0.1-SNAPSHOT.jar spring-prometheus-grafana-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-prometheus-grafana-0.0.1-SNAPSHOT.jar"]
```
##### Create docker-compose file
Create docker compose file which include: spring application, Prometheus, Grafana
`docker-compose` file:
```yaml
# Version of docker compose. See https://docs.docker.com/compose/compose-file/compose-versioning/#versioning
version: "3"

# Services
services:

  application:
    image: spring-application
    container_name: spring-application
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    networks:
      - monitoring-network

  prometheus:
    image: prom/prometheus
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
        - "9090:9090"
    networks:
      - monitoring-network

  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    networks:
      - monitoring-network

networks:
  monitoring-network:
```
##### Build and run
- Build: `mvn clean install`
- Build image: `sudo docker build . -t spring-application`
- Run: `sudo docker-compose up`

