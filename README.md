# monitoring-example
Demo project for Monitoring


```bash
watch -n 1 http -a user:password monitoring-demo.localhost/
```

```bash
watch http -a user:password monitoring-demo.localhost/hello
```

```bash
for counter in {1..5}; do http -a user:password monitoring-demo.localhost/db &; done
```
