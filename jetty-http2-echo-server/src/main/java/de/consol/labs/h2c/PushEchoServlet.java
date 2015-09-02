package de.consol.labs.h2c;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class PushEchoServlet extends HttpServlet {

    private String receivedData = ""; // POST data that we got from the client.

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (request.getMethod()) {
            case "GET":
                if (request.getServletPath().contains("/post-data")) {
                    // This request does not necessarily come from the client. It may be triggered by the PUSH_PROMISE.
                    // However, when responding it does not make a difference if we respond to a client request or to a push promise.
                    response.setContentType("text/plain");
                    response.getWriter().write("Post data: " + receivedData);
                } else {
                    showIndexHtml(response);
                }
                break;
            case "POST":
                handlePost(request, response);
                push(request);
            default:
                showIndexHtml(response);
        }
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        receivedData = IOUtils.readLines(request.getReader()).stream().reduce("", (a, b) -> a + " " + b);
        response.setContentType("text/plain");
        response.getWriter().write("Data received successfully. Retreive data with GET /post-data");
    }

    private void push(HttpServletRequest req) {
        Request baseRequest = Request.getBaseRequest(req);
        if (baseRequest.isPushSupported()) {
            baseRequest
                    .getPushBuilder()
                    .method("GET")
                    .path("/post-data")
                    .push();
        }
    }

    private void showIndexHtml(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("index.html")){
            IOUtils.copy(in, response.getWriter());
        }

    }
}
