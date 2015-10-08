package de.consol.labs.h2c.examples.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http2.HttpUtil;
import io.netty.util.CharsetUtil;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

// This is a slightly modified copy of io.netty.example.http2.client.HttpResponseHandler
public class HttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private SortedMap<Integer, ChannelPromise> streamidPromiseMap;

    public HttpResponseHandler() {
        streamidPromiseMap = new TreeMap<Integer, ChannelPromise>();
    }

    /**
     * Create an association between an anticipated response stream id and a {@link ChannelPromise}
     *
     * @param streamId The stream for which a response is expected
     * @param promise The promise object that will be used to wait/notify events
     * @return The previous object associated with {@code streamId}
     * @see io.netty.example.http2.client.HttpResponseHandler#awaitResponses(long, TimeUnit)
     */
    public ChannelPromise put(int streamId, ChannelPromise promise) {
        return streamidPromiseMap.put(streamId, promise);
    }

    /**
     * Wait (sequentially) for a time duration for each anticipated response
     *
     * @param timeout Value of time to wait for each response
     * @param unit Units associated with {@code timeout}
     * @see io.netty.example.http2.client.HttpResponseHandler#put(int, ChannelPromise)
     */
    public void awaitResponses(long timeout, TimeUnit unit) {
        Iterator<Entry<Integer, ChannelPromise>> itr = streamidPromiseMap.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<Integer, ChannelPromise> entry = itr.next();
            ChannelPromise promise = entry.getValue();
            if (!promise.awaitUninterruptibly(timeout, unit)) {
                throw new IllegalStateException("Timed out waiting for response on stream id " + entry.getKey());
            }
            if (!promise.isSuccess()) {
                throw new RuntimeException(promise.cause());
            }
            itr.remove();
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        Integer streamId = msg.headers().getInt(HttpUtil.ExtensionHeaderNames.STREAM_ID.text());
        if (streamId == null) {
            System.err.println("HttpResponseHandler unexpected message received: " + msg);
            return;
        }

        ChannelPromise promise = streamidPromiseMap.get(streamId);
        if (promise == null) {
            System.err.println("Message received for unknown stream id " + streamId);
        } else {
            // Do stuff with the message (for now just print it)
            ByteBuf content = msg.content();
            if (content.isReadable()) {
                int contentLength = content.readableBytes();
                byte[] arr = new byte[contentLength];
                content.readBytes(arr);
                System.out.println("After " + StopWatch.getInstance().currentTimeInSeconds() + " seconds: " + new String(arr, 0, contentLength));
            }

            promise.setSuccess();
        }
    }
}
