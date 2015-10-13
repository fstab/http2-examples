jetty-http2-server-example
==========================

Embedded Jetty server, started with a `main()` method, featuring a simple 'Hello, World!' Servlet with HTTP/2.

How to Run
----------

```java
git clone https://github.com/fstab/http2-examples.git
cd http2-examples/jetty-http2-server-example
mvn package
```

In order to run the examples, you need
[Jetty's ALPN boot JAR](http://unrestful.io/2015/10/09/alpn-java.html).

Start the server:

```bash
java -Xbootclasspath/p:<path-to-alpn-boot-VERSION.jar> -jar target/jetty-http2-server-example.jar
```

Point your browser to https://localhost:8443

Call using h2c
--------------

```bash
h2c get https://localhost:8443
```
