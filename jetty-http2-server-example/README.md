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

Test using Web Browser
----------------------

Point your browser to [https://localhost:8443](https://localhost:8443)

Test using `h2c`
----------------

Download and install the latest `h2c` release from [github.com/fstab/h2c](https://github.com/fstab/h2c/releases).

```bash
h2c get https://localhost:8443
```
