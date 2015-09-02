Jetty HTTP/2 Server
-------------------

1. Choose the correct version of the ALPN library here: [http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html](http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html)
2. Download here: [http://central.maven.org/maven2/org/mortbay/jetty/alpn/alpn-boot/*$ALPN_VERSION*/alpn-boot-*$ALPN_VERSION*.jar](http://central.maven.org/maven2/org/mortbay/jetty/alpn/alpn-boot/$ALPN_VERSION/alpn-boot-$ALPN_VERSION.jar)
3. Run with `java -Xbootclasspath/p:alpn-boot-8.1.3.v20150130.jar -jar target/jetty-http2-echo-server-1.0-SNAPSHOT.jar`

