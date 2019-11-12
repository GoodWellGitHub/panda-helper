package com.wa.hj.jedis.message.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.concurrent.*;

public class Messager {
    private static final Logger logger = LoggerFactory.getLogger(Messager.class);
    private static final String Q_RPEFIX = "message:q:";
    private static final ExecutorService topPool;
    private static final ExecutorService consumerPool;
    private JedisPool jedisPool;

    public Messager(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    static {
        topPool = new ThreadPoolExecutor(30, 50, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("fail execute q thread {}", t.getName(), e);
                    }
                });
                return t;
            }
        });

        consumerPool = new ThreadPoolExecutor(30, 50, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("fail execute q thread {}", t.getName(), e);
                    }
                });
                return t;
            }
        });
    }

    public void sendMessage(String topic, String message) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(Q_RPEFIX + topic, message);
        } catch (Exception e) {
            logger.error("Failed send message .", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public <T> void consume(Consumer consumer) {
        final String topic = Q_RPEFIX + consumer.getTopic();
        topPool.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Jedis jedis = null;
                    try {
                        jedis = jedisPool.getResource();
                        List<String> messageList = jedis.blpop(5, topic);
                        if (messageList.size() == 2) {
                            try {
                                consumerPool.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            consumer.onMessage(messageList.get(1));
                                        } catch (Exception e) {
                                            logger.error("failed consume message", e);
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                logger.error("failed submit message", e);
                            }

                        }
                    } catch (Exception e) {
                        logger.error("failed get message from jedis", e);
                    } finally {
                        if (jedis != null) {
                            try {
                                jedis.close();
                            } catch (Exception e) {
                                logger.error("Failed get message from redis.", e);
                            }
                        }
                    }
                }
            }
        });
    }
}
