package com.example.Controller;

import com.example.Service.StatisticsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    public BarChart barChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private Text txtQuaHan;

    @FXML
    private Text txtSoLuongKhach;

    @FXML
    private Text txtSoSachDangMuon;

    @FXML
    private Text txtSoLuongSach;

    private final StatisticsService statisticsService;

    public StatisticsController() {
        this.statisticsService = new StatisticsService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // tu khoi chay khi vao ung dung
        System.out.println("run");

        try {
            PieChart.Data overdueData = new PieChart.Data("Quá hạn", statisticsService.getTotalBorrowLate());
            PieChart.Data returnedData = new PieChart.Data("Tổng số lượt mượn", statisticsService.getAllBorrowedBooks());
            pieChart.setData(FXCollections.observableArrayList(overdueData, returnedData));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            int totalCustomers = statisticsService.getTotalCustomers();
            int totalBorrowedBooks = statisticsService.getTotalBorrowedBooks();
            int totalBooks = statisticsService.getTotalBooks();
            int totalBorrowLate = statisticsService.getTotalBorrowLate();

            txtSoLuongKhach.setText(Integer.toString(totalCustomers));
            txtSoSachDangMuon.setText(Integer.toString(totalBorrowedBooks));
            txtSoLuongSach.setText(Integer.toString(totalBooks));
            txtQuaHan.setText(Integer.toString(totalBorrowLate));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
