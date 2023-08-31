# monitoring-example
Demo project for Monitoring


```bash
watch -n 1 http -a user:password monitoring-demo.localhost/
```

```bash
watch http -a user:password monitoring-demo.localhost/hello
```

```bash
for counter in {1..5}; do http -a user:password monitoring-demo.localhost/db & done
```

# initial demo

1. present demo app endpoints - /, /hello, /db
2. start bash requests
3. show that, with no probes configured, we have periods of unavailability

# health check

1. pom.xml - add actuator dependency
   ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
   ```

2. test /actuator
3. add exemption for authentication for /health so that it can be used in k8s probes
   ```kotlin
      it.requestMatchers(EndpointRequest.to(HealthEndpoint::class.java)).permitAll()
   ```
4. configure k8s sts to use probes (liveness, readiness, deploy, show slow startup)
    ```yaml
    livenessProbe:
      httpGet:
        port: http
        path: /actuator/health/liveness
      failureThreshold: 3
    readinessProbe:
      httpGet:
        port: http
        path: /actuator/health/readiness
      periodSeconds: 60
      failureThreshold: 1
    ```
5. configure startup
   ```yaml
     startupProbe:
       httpGet:
         port: http
         path: /actuator/health/readiness
       failureThreshold: 100
       periodSeconds: 1
   ```

6. deploy, show fast start and endpoints: ../liveness & ../readiness 
7. show details when authorized
   ```yaml
   management:
     endpoint:
       health:
         show-details: when_authorized
   ```
8. turn off the database, show that liveness & readiness do not fail

9. include db into readiness
   ```yaml
   management:
     endpoint:
       health:
         show-details: when_authorized
         group:
           readiness:
             include:
             - readinessState
             - db
   ```


# Enable monitoring

1. pom.xml - add micrometer dependency
   ```xml
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
   ```

2. expose prometheus endpoint
   ```yaml
   endpoints:
     web:
       exposure:
         include:
         - info
         - health
         - prometheus
   ```
   
3. add security exemption for metrics
   ```kotlin
   it.requestMatchers(EndpointRequest.to(InfoEndpoint::class.java, HealthEndpoint::class.java, PrometheusScrapeEndpoint::class.java)).permitAll()
   ```


# getting metrics to prometheus

1. start requests for / and /hello
2. show servicemonitor
3. show prometheus & queries
4. show grafana dashboard



# check out
