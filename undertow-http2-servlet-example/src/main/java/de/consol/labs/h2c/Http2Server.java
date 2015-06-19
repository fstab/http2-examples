package de.consol.labs.h2c;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.net.ssl.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

import static io.undertow.servlet.Servlets.*;

public class Http2Server {

    private static final char[] STORE_PASSWORD = "password".toCharArray();
    public static final String MYAPP = "/hello-world";

    public static void main(final String[] args) throws Exception {

        assertJavaVersion8();

        DeploymentInfo servletBuilder = deployment()
                .setClassLoader(HelloWorldServlet.class.getClassLoader())
                .setContextPath(MYAPP)
                .setDeploymentName("hello-world.war")
                .addServlets(
                        servlet("HelloWorldServlet", HelloWorldServlet.class)
                                .addInitParam("message", "Hello World")
                                .addMapping("/api/hello-world"));

        DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        HttpHandler servletHandler = manager.start();
        PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
                .addPrefixPath(MYAPP, servletHandler);

        String bindAddress = System.getProperty("bind.address", "localhost");
        SSLContext sslContext = createSSLContext(loadKeyStore("server.keystore"), loadKeyStore("server.truststore"));
        Undertow server = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpsListener(8443, bindAddress, sslContext)
                .setHandler(path)
                .build();

        server.start();
    }

    private static void assertJavaVersion8() {
        String version = System.getProperty("java.version");
        System.out.println("Java version " + version);
        if(version.charAt(0) == '1' && Integer.parseInt(version.charAt(2) + "") < 8 ) {
            System.out.println("This example requires Java 1.8 or later");
            System.out.println("The HTTP2 spec requires certain cyphers that are not present in older JVM's");
            System.out.println("See section 9.2.2 of the HTTP2 specification for details");
            System.exit(1);
        }
    }

    private static KeyStore loadKeyStore(String name) throws Exception {
        String storeLoc = System.getProperty(name);
        final InputStream stream;
        if(storeLoc == null) {
            stream = Http2Server.class.getResourceAsStream(name);
        } else {
            stream = Files.newInputStream(Paths.get(storeLoc));
        }

        try(InputStream is = stream) {
            KeyStore loadedKeystore = KeyStore.getInstance("JKS");
            loadedKeystore.load(is, STORE_PASSWORD);
            return loadedKeystore;
        }
    }

    private static SSLContext createSSLContext(final KeyStore keyStore, final KeyStore trustStore) throws Exception {
        KeyManager[] keyManagers;
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, STORE_PASSWORD);
        keyManagers = keyManagerFactory.getKeyManagers();

        TrustManager[] trustManagers;
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        trustManagers = trustManagerFactory.getTrustManagers();

        SSLContext sslContext;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, null);

        return sslContext;
    }
}
