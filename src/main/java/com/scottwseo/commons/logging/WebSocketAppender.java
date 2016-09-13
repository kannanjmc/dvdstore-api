package com.scottwseo.commons.logging;

import ch.qos.logback.core.AppenderBase;

public class WebSocketAppender<E> extends AppenderBase<E> {

    @Override
    protected void append(E eventObject) {
        LogEndPoint.sendAll("" + eventObject);
    }

    @Override
    public void start() {
        super.start();
    }

}
