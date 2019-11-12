package com.wa.hj.jedis.message.demo;

import com.wa.hj.jedis.message.util.Consumer;
import com.wa.hj.jedis.message.util.Messager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class SayHelloConsumer extends Consumer {
    @Autowired
    private Messager messager;

    public SayHelloConsumer() {
        super("hello");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("hello" + message);
    }

    @PostConstruct
    public void init() {
        messager.consume(this);
    }
}
