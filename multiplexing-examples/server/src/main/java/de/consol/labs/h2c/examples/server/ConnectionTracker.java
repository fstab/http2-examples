package de.consol.labs.h2c.examples.server;

import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionTracker {

    private static ConnectionTracker instance = new ConnectionTracker();

    private AtomicInteger nConnections = new AtomicInteger(0);

    public static ConnectionTracker getInstance() {
        return instance;
    }

    private ConnectionTracker() {
    }

    public void onConnection() {
        nConnections.incrementAndGet();
    }

    public int getNumberOfConnections() {
        return nConnections.get();
    }
}
