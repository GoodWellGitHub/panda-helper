package com.org.database.config;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public abstract class ResourceManagement extends LogResourceManagement {


    protected PrintWriter logWriter = new PrintWriter(System.out);

    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return logWriter;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }


}
