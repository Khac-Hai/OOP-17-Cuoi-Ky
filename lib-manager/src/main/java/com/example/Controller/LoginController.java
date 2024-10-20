package com.example.Controller;

import com.example.App;
import com.example.Service.AccountService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {
    @FXML
    private Label lbErr;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private final AccountService accountService;

    public LoginController(){
        this.accountService = new AccountService();
    }

    public void onClickLogin(ActionEvent actionEvent) throws SQLException, IOException {
        if(accountService.doLogin(txtUsername.getText(), txtPassword.getText()) == 0){
            lbErr.setText("*Tài khoản hoặc mật khẩu trống");
            return;
        }

        if(accountService.doLogin(txtUsername.getText(), txtPassword.getText()) == 1){
            lbErr.setText("*Tài khoản hoặc mật khẩu không chính xác");
            return;
        }

        App.setRoot("DashBoardFrm");
    }

    public void onClickRegister(ActionEvent actionEvent) throws IOException {
        App.setRootPop("register", "Đăng kí", false, Optional.empty());
    }
}
