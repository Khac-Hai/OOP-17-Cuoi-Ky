package com.example.Models;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Borrow {
    private String customerId;
    private String bookId;
    private String bookName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private String status;

    public Borrow(String customerId, String bookId, String bookName, LocalDate startDate, LocalDate endDate, LocalDate returnDate, String status) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
