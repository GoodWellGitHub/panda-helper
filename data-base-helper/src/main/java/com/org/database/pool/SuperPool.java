package com.org.database.pool;

import com.org.database.config.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class SuperPool {
    private static final Logger logger = LoggerFactory.getLogger(SuperPool.class);
    private DatabaseConfiguration configuration;

    //已使用的连接
    private ArrayBlockingQueue<SuperPoolConnection> use = new ArrayBlockingQueue<SuperPoolConnection>(1024);

    //未使用的连接
    private ArrayBlockingQueue<SuperPoolConnection> duse = new ArrayBlockingQueue<SuperPoolConnection>(1024);

    private boolean initDriver;

    public SuperPool() {
    }

    private void initDriver() {
        try {
            Class.forName(configuration.getDriverClass());
        } catch (Exception e) {
            logger.warn("驱动加载失败....");
        }
        initDriver = true;
    }

    public SuperPoolConnection getConnection() {
        if (!initDriver) {
            initDriver();
        }
        Connection connection = null;
        SuperPoolConnection superPoolConnection = null;
        try {
            connection = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getUserName(), configuration.getPassword());
            superPoolConnection = new SuperPoolConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return superPoolConnection;
    }

    private static class singleton {
        private static SuperPool superPool;

        static {
            superPool = new SuperPool();
        }

        public static SuperPool getInstance() {
            return superPool;
        }
    }

    public SuperPoolConnection getConnection(long waitTime) throws InterruptedException {
        SuperPoolConnection connection = null;
        while (true) {
            if (use.size() + duse.size() <= getConfiguration().getMaxIdle()) {
                if (duse.size() <= 0) {
                    connection = getConnection();
                    use.put(connection);
                    return connection;
                } else {
                    return duse.take();
                }
            } else {
                wait(waitTime);
            }
        }
    }

    public DatabaseConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(DatabaseConfiguration configuration) {
        this.configuration = configuration;
    }

    public static SuperPool getInstance() {
        return singleton.getInstance();
    }

    public void addUse(SuperPoolConnection connection) {
        this.use.add(connection);
    }

    public void removeUse(SuperPoolConnection connection) {
        this.use.remove(connection);
    }

    public void addDuse(SuperPoolConnection connection) {
        this.duse.add(connection);
    }

    public void removeDuse(SuperPoolConnection connection) {
        this.duse.remove(connection);
    }

    public ArrayBlockingQueue<SuperPoolConnection> getUse() {
        return use;
    }

    public void setUse(ArrayBlockingQueue<SuperPoolConnection> use) {
        this.use = use;
    }

    public ArrayBlockingQueue<SuperPoolConnection> getDuse() {
        return duse;
    }

    public void setDuse(ArrayBlockingQueue<SuperPoolConnection> duse) {
        this.duse = duse;
    }
}
