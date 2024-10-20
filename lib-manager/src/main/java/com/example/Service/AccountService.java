package com.example.Service;

import com.example.Utils.ExecuteQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AccountService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    private Boolean isEmpty(String username, String password) { // kiểm tra rỗng
        return username.isEmpty() || password.isEmpty();
    }

    public int doLogin(String username, String password) throws SQLException { // thực hiện đăng nhập
        if (isEmpty(username, password)) {
            return 0;
        }

        if (!checkAccount(username, password)) {
            return 1;
        }
        return 2;
    }

    private Boolean checkAccount(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement statement = executeQuery.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }
        return false;
    }

    public boolean register(String username, String password) throws SQLException { // đăng kí tài khoản mới
        if (checkAccount(username, password)) { // kiểm tra tài khoản tồn tại chưa
            return false;
        }

        String sql = String.format("insert into user(username, password) values('%s', '%s')", username, password); // câu lệnh insert
        executeQuery.executeUpdate(sql);
        return true;
    }
}
