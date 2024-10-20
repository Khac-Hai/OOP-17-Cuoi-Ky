package com.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ExecuteQuery { // kết nối cơ sở dữ liệu + truy vấn
    // singleton pattern
    private static ExecuteQuery instance; // tạo một thực thể của chính nó

    private final static String jdbcUrl = "jdbc:mysql://avnadmin:AVNS_iPSg1Q09vcPg0998rGJ@mysql-b33d979-defaudb.h.aivencloud.com:21965/defaultdb?ssl-mode=REQUIRED";
    private final static String username = "avnadmin";
    private final static String password = "AVNS_iPSg1Q09vcPg0998rGJ";

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

    public ResultSet executeQuery(String sql) { // những câu query có trả về giá trị (select)
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String sql) { // những câu query không trả về giá trị (update, insert, delete)
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
