# Spring & Sentry
### Dependencies
Adding dependencies in `pom.xml`
```xml
<dependencies>
    ...
    <dependency>
        <groupId>io.sentry</groupId>
        <artifactId>sentry-spring</artifactId>
        <version>1.7.27</version>
    </dependency>
    <dependency>
        <groupId>io.sentry</groupId>
        <artifactId>sentry-logback</artifactId>
        <version>1.7.27</version>
    </dependency>
    ...
</dependencies>
```

### Configure sentry
#### Sentry properties file
Add `sentry.properties` file in resource folder
```properties
release=1.0.0
tags=service:gateway
stacktrace.app.packages=com.github.halab4dev


async.queuesize=100
timeout=1000
```

#### Configuration spring beans
Create `SentryConfiguration` class with content below:
```java
@Configuration
public class SentryConfiguration {

    @PostConstruct
    public void init() {
        Sentry.init("<DSN_URL>?environment=staging");
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }
}
```
#### Integrating with logback
Add `logback-spring.xml` in `resource` folder:
```xml
<configuration>
    <!--    Include spring boot default config-->
    <include resource="org/springframework/boot/logging/logback/base.xml"/>

    <!--    Configure the Sentry appender, overriding the logging threshold to the WARN level -->
    <appender name="Sentry" class="io.sentry.logback.SentryAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
    </appender>

    <!--    Enable the Console and Sentry appenders, Console is provided as an example
        of a non-Sentry logger that is set to a different logging threshold -->
    <root level="INFO">
        <appender-ref ref="Sentry" />
    </root>
</configuration>

```