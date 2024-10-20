package com.example.Service;

import com.example.Utils.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public int getTotalCustomers() throws SQLException {
        String getTotalCustomers = "SELECT COUNT(*) FROM customer where is_delete = 0";
        ResultSet resultSet = executeQuery.executeQuery(getTotalCustomers);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getTotalBorrowedBooks() throws SQLException {
        String getTotalBorrowedBooks = "SELECT COUNT(*) FROM borrow WHERE status = 0";
        ResultSet resultSet = executeQuery.executeQuery(getTotalBorrowedBooks);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getTotalBooks() throws SQLException {
        String getTotalBooks = "SELECT Sum(amount) as total FROM book where is_delete = 0";
        ResultSet resultSet = executeQuery.executeQuery(getTotalBooks);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getTotalBorrowLate() throws SQLException { // lay so luong sach dang bi muon qua han
        String getTotalBorrowLateSql = "SELECT COUNT(*) AS total_overdue_books\n" +
                "FROM borrow\n" +
                "WHERE status = 0 AND return_date IS NULL AND (end_day < CURRENT_DATE OR (end_day = CURRENT_DATE AND CURRENT_TIME > '23:59:59'));\n";
        ResultSet resultSet = executeQuery.executeQuery(getTotalBorrowLateSql);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getAllBorrowedBooks() throws SQLException { // lay tong so luot muon sach
        String getAllBorrowedBooks = "SELECT * FROM borrow";
        ResultSet resultSet = executeQuery.executeQuery(getAllBorrowedBooks);

        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
