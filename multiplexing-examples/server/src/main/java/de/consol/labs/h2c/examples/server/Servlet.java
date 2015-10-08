package de.consol.labs.h2c.examples.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Servlet extends HttpServlet {

    private static final int DELAY_IN_SECONDS = 6;
    private final ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
    private final AtomicInteger numberOfRequests = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int numberOfCurrentRequest = numberOfRequests.incrementAndGet();
        int numberOfCurrentConnection = connectionTracker.getNumberOfConnections();
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(DELAY_IN_SECONDS));
        } catch (InterruptedException e) {
            throw new ServletException(e);
        }
        resp.setContentType("text/plain");
        resp.getWriter().write("This is the response for request number " + numberOfCurrentRequest +
                " using connection number " + numberOfCurrentConnection +
                " after " + DELAY_IN_SECONDS + " seconds delay.");
    }
}
