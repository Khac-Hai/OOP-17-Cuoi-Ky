package com.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQuery { // ket noi co so du lieu + truy van
    // singleton pattern
    private static ExecuteQuery instance; // tao mot thuc the cua chinh no

    private final static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/lib";
    private final static String username = "root";
    private final static String password = "";

    private Connection connection;

    private ExecuteQuery() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ExecuteQuery getInstance() {
        if(instance == null) {
            synchronized (ExecuteQuery.class) {
                if (instance == null) {
                    instance = new ExecuteQuery();
                }
            }
        }
        return instance;
    }

    public ResultSet executeQuery(String sql) { // nhung cau query co tra ve gia tri (select)
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String sql) { // nhung cau query khong tra ve gia tri // update, insert, delete
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}