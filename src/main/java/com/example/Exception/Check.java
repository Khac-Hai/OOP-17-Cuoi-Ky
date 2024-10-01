package com.example.Exception;

import java.util.HashMap;
import java.util.Map;

class Book {
    private String title;
    private int stock;

    public Book(String title, int stock) {
        this.title = title;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    public void borrowBook() throws BookOutOfStockException {
        if (stock == 0) {
            throw new BookOutOfStockException("Sách '" + title + "' đã hết.");
        }
        stock--;
        System.out.println("Bạn đã mượn sách '" + title + "'. Số lượng còn lại: " + stock);
    }
}

class Library {
    private Map<String, Book> books = new HashMap<>();

    public void addBook(String title, int stock) {
        books.put(title, new Book(title, stock));
    }

    public Book findBook(String title) throws BookNotFoundException {
        Book book = books.get(title);
        if (book == null) {
            throw new BookNotFoundException("Sách '" + title + "' không tồn tại trong thư viện.");
        }
        return book;
    }

    public void borrowBook(String title) throws BookNotFoundException, BookOutOfStockException {
        Book book = findBook(title);
        book.borrowBook();
    }
}

public class Check {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("Lập Trình Java", 5);
        library.addBook("Quản Lý Dự Án", 0);

        try {
            library.borrowBook("Lập Trình Java");
            library.borrowBook("Quản Lý Dự Án");
        } catch (BookNotFoundException | BookOutOfStockException ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }
    }
}

