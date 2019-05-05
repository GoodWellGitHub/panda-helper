package com.org.database;

import com.org.database.config.SuperDataSource;

import java.sql.Connection;

public class DatabaseDemo {
    public static void main(String[] args) throws Exception {
        SuperDataSource dataSource = new SuperDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/practice");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(1);
        dataSource.setMaxIdle(20);
        Connection a = dataSource.getConnection();
        System.out.println(a.toString());
        //连接进行未进行关闭
//	  a.close();
        Connection b = dataSource.getConnection();
        System.out.println(b.toString());
    }
}
