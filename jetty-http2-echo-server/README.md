Jetty HTTP/2 Server
-------------------

1. Choose the correct version of the ALPN library here: [http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html](http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html)
2. Download ALPN here (replace $ALPN_VERSION with the version found above): [http://central.maven.org/maven2/org/mortbay/jetty/alpn/alpn-boot/*$ALPN_VERSION*/alpn-boot-*$ALPN_VERSION*.jar](http://central.maven.org/maven2/org/mortbay/jetty/alpn/alpn-boot/$ALPN_VERSION/alpn-boot-$ALPN_VERSION.jar)
3. Run with `java -Xbootclasspath/p:alpn-boot-$ALPN_VERSION.jar -jar target/jetty-http2-echo-server.jar`

