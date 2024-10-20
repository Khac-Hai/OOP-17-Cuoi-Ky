package com.example.Controller;

import com.example.App;
import com.example.Helper.AlertHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ListView<String> listMenu;

    private ObservableList<String> items = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.add("Quản lý sách");
        items.add("Quản lý khách hàng");
        items.add("Quản lý mượn trả sách");
        listMenu.setItems(items);

        listMenu.getSelectionModel().selectFirst();
        loadPane(listMenu.getSelectionModel().getSelectedItem());
    }

    public void selectedItem(MouseEvent mouseEvent) {
        String selectedItem = listMenu.getSelectionModel().getSelectedItem();
        if(selectedItem == null) return;
        loadPane(selectedItem);
    }

    private void loadPane(String selected){
        String frm = "";
        switch (selected){
            case "Quản lý sách":
                frm = "/com/example/QuanLySachFrm.fxml";
                break;
            case "Quản lý khách hàng":
                frm = "/com/example/QuanLyKhachHangFrm.fxml";
                break;
            case "Quản lý mượn trả sách":
                frm = "/com/example/QuanLyMuonTraFrm.fxml";
                break;
            default:
                frm = "com/example/QuanLySachFrm.fxml";
                break;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
            pane.getChildren().clear();
            AnchorPane adminPane = loader.load();
            pane.getChildren().add(adminPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickLogout(ActionEvent actionEvent) throws IOException {
        if(AlertHelper.showConfirmation("Bạn có chắc chắn muốn đăng xuất không?")){
            App.setRoot("Login");
        }
    }
}
