multiplexing-examples
=====================

This project contains some examples of
[HTTP/2 multiplexing](https://httpwg.github.io/specs/rfc7540.html#StreamsLayer),
i.e. HTTP/2's ability to handle multiple requests within one TCP connection
independent of each other, so a blocked or stalled request or response does
not prevent progress on other streams.

A blog post on the examples can be found on [http://unrestful.io](http://unrestful.io/2015/10/10/http2-java-client-examples.html).

What does it do?
----------------

The server takes GET requests and answers them after 6 seconds delay.
Each client sends three GET requests over a single connection.
With HTTP/1 this would imply that the last response is received after 3*6=18 seconds.
As the clients use HTTP/2, all requests are processed in parallel, so all
three responses are received after 6 seconds.

The demo is implemented three times using three different HTTP/2 client libraries:

  * [jetty](http://www.eclipse.org/jetty)
  * [netty](http://netty.io/)
  * [OkHttp](http://square.github.io/okhttp/)

Prerequisites
-------
Java 8

How to Run
----------

```java
git clone https://github.com/fstab/http2-examples.git
cd http2-examples/multiplexing-examples
mvn package
```

In order to run the examples, you need
[Jetty's ALPN boot JAR](http://unrestful.io/2015/10/09/alpn-java.html).  Make sure to download the corresponding file or else it will not work.

Start the server:

```bash
java -Xbootclasspath/p:<path-to-alpn-boot-VERSION.jar> -jar server/target/server.jar
```

Run one of the clients:

```bash
java -Xbootclasspath/p:<path-to-alpn-boot-VERSION.jar> -jar jetty-client/target/jetty-client.jar
java -Xbootclasspath/p:<path-to-alpn-boot-VERSION.jar> -jar netty-client/target/netty-client.jar
java -Xbootclasspath/p:<path-to-alpn-boot-VERSION.jar> -jar okhttp-client/target/okhttp-client.jar
```
