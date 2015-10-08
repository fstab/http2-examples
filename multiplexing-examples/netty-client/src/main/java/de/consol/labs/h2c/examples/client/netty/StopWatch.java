package de.consol.labs.h2c.examples.client.netty;

import java.util.concurrent.TimeUnit;

/**
 * Created by fabian on 07/10/15.
 */
public class StopWatch {

    private static StopWatch instance = new StopWatch();

    public static StopWatch getInstance() {
        return instance;
    }

    private long startTime;

    private StopWatch() {}

    public void start() {
        startTime = System.nanoTime();
    }

    public int currentTimeInSeconds() {
        long now = System.nanoTime();
        return (int) TimeUnit.NANOSECONDS.toSeconds(now - startTime);
    }
}
