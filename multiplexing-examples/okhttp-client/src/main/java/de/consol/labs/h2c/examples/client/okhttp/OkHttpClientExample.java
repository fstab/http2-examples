package de.consol.labs.h2c.examples.client.okhttp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.eclipse.jetty.util.ssl.SslContextFactory.TRUST_ALL_CERTS;

public class OkHttpClientExample {

    // In order to run this, you need the alpn-boot-XXX.jar in the bootstrap classpath.
    public static void main(String[] args) throws Exception {
        OkHttpClient client = getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url("https://localhost:8443") // The Http2Server should be running here.
                .build();
        long startTime = System.nanoTime();
        for (int i=0; i<3; i++) {
            Thread.sleep(1000); // http://stackoverflow.com/questions/32625035/when-using-http2-in-okhttp-why-multi-requests-to-the-same-host-didnt-use-just
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }
                public void onResponse(Response response) throws IOException {
                    long duration = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                    System.out.println("After " + duration + " seconds: " + response.body().string());
                }
            });
        }
    }

    // http://stackoverflow.com/questions/25509296/trusting-all-certificates-with-okhttp
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, TRUST_ALL_CERTS, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier((hostname, session) -> true);

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
