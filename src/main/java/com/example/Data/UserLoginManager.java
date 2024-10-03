package com.example.Data;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class UserLoginManager {
    private static final String DIRECTORY_PATH = "Data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/Login.txt";

    public static void saveUserLogin(String username, String password) {
        try {
            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(username + "," + password);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Có lỗi khi ghi dữ liệu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        saveUserLogin("user1", "pass1");
        saveUserLogin("user2", "pass2");

    }
}

