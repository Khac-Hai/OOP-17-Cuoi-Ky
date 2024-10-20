package com.example.Controller;

import com.example.Helper.AlertHelper;
import com.example.Service.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtRePassword;
    private final AccountService accountService;

    public RegisterController() {
        accountService = new AccountService();
    }

    public void onSubmit(ActionEvent actionEvent) throws SQLException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();

        if (password.equals(rePassword)) {
            boolean registered = accountService.register(username, password);

            if (registered) {
                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Lỗi", null, "Đăng kí thành công!");

            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Tài khoản đã tồn tại!");

            }

        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Mật khẩu nhập lại không khớp!");
        }


    }
}
