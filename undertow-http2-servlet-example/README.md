Undertow HTTP/2 Servlet Example
===============================

Build and run as follows:

```bash
git clone https://github.com/fstab/http2-examples
cd http2-examples/undertow-http2-servlet-example
mvn clean package
java -jar target/undertow-http2-servlet-example.jar
```

Then point an HTTP/2-enabled web browser to [https://localhost:8443/hello-world/api/hello-world](https://localhost:8443/hello-world/api/hello-world)

Notice
------

This example was created using the the _http2_ example and the _servlet_ example in [undertow-io/undertow](https://github.com/undertow-io/undertow/tree/master/examples/src/main/java/io/undertow/examples).
