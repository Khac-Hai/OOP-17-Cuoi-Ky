package com.example.Models;
import java.sql.Timestamp;
public class Book { // dai dien cho cac thuc the trong csdl
    private int bookId;
    private String name;
    private int categoryId;
    private int amount;
    private Timestamp createDay;

    public Book(int id, String name, int categoryId, int amount, Timestamp createDay) {
        this.bookId = id;
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.createDay = createDay;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getCreateDay() {
       return createDay;
    }

    public void setCreateDay(Timestamp createDay) {
        this.createDay = createDay;
    }
}
