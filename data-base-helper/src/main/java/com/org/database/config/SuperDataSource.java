package com.org.database.config;

import com.org.database.pool.SuperPool;
import com.org.database.pool.SuperPoolConnection;
import com.org.uitl.StringUtils;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class SuperDataSource extends ResourceManagement implements DataSource, ConnectionPoolDataSource {
    protected volatile String username;
    protected volatile String password;
    protected volatile String jdbcUrl;
    protected volatile String driverClass;
    protected volatile int initialSize = 0;
    protected volatile int minIdle = 0;
    protected volatile int maxIdle = 0;
    protected volatile int maxActive = 0;
    protected volatile long maxWait = 0;
    protected volatile long waitTime = 1000L;

    private SuperPool superPool = SuperPool.getInstance();

    public SuperDataSource() {
    }

    public SuperDataSource(String username, String password, String jdbcUrl) {
        super();
        this.username = username;
        this.password = password;
        this.jdbcUrl = jdbcUrl;
    }

    public SuperPoolConnection getSuperPoolConnection() {
        init();
        try {
            return superPool.getConnection(getWaitTime());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void init() {
        if (superPool.getConfiguration() == null) {
            DatabaseConfiguration configuration = new DatabaseConfiguration();
            configuration.setDriverClass(getDriverClass());
            configuration.setJdbcUrl(getJdbcUrl());
            configuration.setUserName(getUsername());
            configuration.setPassword(getPassword());
            configuration.setInitSize(getInitialSize());
            configuration.setMaxActive(getMaxActive());
            configuration.setMaxIdle(getMaxIdle());
            configuration.setMaxAwait(getMaxWait());
            configuration.setMinIdle(getMinIdle());
            superPool.setConfiguration(configuration);
        }
    }


    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        SuperPoolConnection superPoolConnection = getSuperPoolConnection();
        if (superPoolConnection != null) {
            return superPoolConnection;
        } else {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }
    }

    @Override
    public PooledConnection getPooledConnection(String user, String password) throws SQLException {
        if (this.username == null && this.password == null && user != null && password != null) {
            this.username = user;
            this.password = password;
        }
        if (StringUtils.equals(user, this.username)) {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }
        if (!StringUtils.equals(password, this.password)) {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }
        return getPooledConnection();
    }

    public void close() throws SQLException {
        for (Connection connection : superPool.getUse()) {
            connection.close();
        }
        superPool.setUse(null);
        for (Connection connection : superPool.getDuse()) {
            connection.close();
        }
        superPool.setDuse(null);
    }

    @Override
    public Connection getConnection() throws SQLException {
        SuperPoolConnection superPoolConnection = getSuperPoolConnection();
        if (superPoolConnection != null) {
            return superPoolConnection;
        } else {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (this.username == null && this.password == null && username != null && password != null) {
            this.username = username;
            this.password = password;
            return getConnection();
        }

        if (!StringUtils.equals(username, this.username)) {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }

        if (!StringUtils.equals(password, this.password)) {
            throw new UnsupportedOperationException("Not supported by SuperDataSource");
        }
        return getConnection();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public SuperPool getSuperPool() {
        return superPool;
    }

    public void setSuperPool(SuperPool superPool) {
        this.superPool = superPool;
    }
}
