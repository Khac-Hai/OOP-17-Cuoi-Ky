package com.example.DTO;

import java.time.LocalDate;

public class BookDTO {
    private String bookId;
    private String bookName;
    private String authorName;
    private String categoryName;
    private LocalDate publishDate;
    private int quantity;

    public BookDTO(String bookId, String bookName, String authorName, String categoryName, LocalDate publishDate, int quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.publishDate = publishDate;
        this.quantity = quantity;
    }

    public BookDTO(String bookName, String authorName, String categoryName, int quantity, LocalDate publishDate){ // use for addBookServices
        this.bookName = bookName;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.publishDate = publishDate;
        this.quantity = quantity;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", publishDate=" + publishDate +
                ", quantity=" + quantity +
                '}';
    }
}
