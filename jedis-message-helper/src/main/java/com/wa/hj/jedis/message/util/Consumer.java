package com.wa.hj.jedis.message.util;

public abstract class Consumer {
    protected String topic;

    public Consumer(String topic) {
        this.topic = topic;
    }

    public abstract void onMessage(String message);

    public String getTopic() {
        return this.topic;
    }
}
