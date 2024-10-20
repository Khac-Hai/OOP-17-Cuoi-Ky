package com.example.Controller;

import com.example.App;
import com.example.Exception.EmailNotValidException;
import com.example.Exception.IsExistedException;
import com.example.Exception.NullException;
import com.example.Exception.PhoneNumberNotValidException;
import com.example.Models.Customer;
import com.example.Service.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.Helper.AlertHelper.showAlert;
import static com.example.Helper.TableHelper.showOnTable;

public class CustomerController implements Initializable {
    @FXML
    private Label lbErrEmail;
    @FXML
    private Label lbErrPhoneNumber;
    @FXML
    private Label lbErrID;
    @FXML
    private Label lbErrName;
    @FXML
    private TableView<Customer> tbCustomer;
    @FXML
    private TableColumn<Customer,Integer> colId;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer,String> colEmail;
    @FXML
    private TableColumn<Customer,String> colPhoneNumber;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    private final CustomerService customerService;

    public CustomerController(){
        this.customerService = new CustomerService();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) { // tu khoi tao nhung thu trong no trong lan chay dau tien


        // regis event
        customerService.setTextFieldClearErrorOnTyping(txtId, lbErrID);
        customerService.setTextFieldClearErrorOnTyping(txtName, lbErrName);
        customerService.setTextFieldClearErrorOnTyping(txtEmail, lbErrEmail);
        customerService.setTextFieldClearErrorOnTyping(txtPhoneNumber, lbErrPhoneNumber);

        setCell();
        try {
            showOnTable(tbCustomer,customerService.customersData(),colId,colName,colPhoneNumber,colEmail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell() {
        colId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("identityCard"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
    }

    public void onSelected(MouseEvent mouseEvent) { // khi chon thi se hien thi len cac o text
        Customer customer = tbCustomer.getSelectionModel().getSelectedItem();
        if(customer != null){

            txtId.setText(customer.getIdentityCard());
            txtName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtPhoneNumber.setText(customer.getPhoneNumber());

            CustomerBorrowController.setCustomerId(customer.getIdentityCard());
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws SQLException {
        lbErrEmail.setText("");
        lbErrPhoneNumber.setText("");

        try {
            customerService.addCustomer(txtId.getText(),txtName.getText(), txtEmail.getText(), txtPhoneNumber.getText()); // thuc hien them khach moi
        } catch (NullException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", null, e.getMessage());
        } catch (EmailNotValidException e) {
            lbErrEmail.setText(e.getMessage());
            return;
        } catch (PhoneNumberNotValidException e) {
           lbErrPhoneNumber.setText(e.getMessage());
           return;
        } catch (IsExistedException e) {
            if(e.getMessage().equals("*Số điện thoại đã tồn tại trong hệ thống")){
                lbErrPhoneNumber.setText(e.getMessage());
                return;
            }

            if(e.getMessage().equals("*Email đã tồn tại trong hệ thống")){
                lbErrEmail.setText(e.getMessage());
                return;
            }

            if(e.getMessage().equals("*Id đã tồn tại trong hệ thống")){
                lbErrID.setText(e.getMessage());
                return;
            }
        }
        tbCustomer.setItems(customerService.customersData());
        clear();
    }


    public void onClickDelete(ActionEvent actionEvent) throws SQLException {
        String customerId = tbCustomer.getSelectionModel().getSelectedItem().getIdentityCard();
        customerService.deleteCustomer(customerId);
        tbCustomer.setItems(customerService.customersData());
        customerService.clear(txtEmail,txtId,txtName,txtPhoneNumber);
    }

    public void onClickUpdate(ActionEvent actionEvent) throws SQLException {
        String oldId = null;

        Customer selectedCustomer = tbCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            oldId = selectedCustomer.getIdentityCard();
        } else {
            oldId = txtId.getId();
        }

        try {
            customerService.updateCustomer(oldId, txtName.getText(), txtEmail.getText(),txtPhoneNumber.getText(),txtId.getText());
        } catch (NullException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", null, e.getMessage());
        } catch (EmailNotValidException e) {
            lbErrEmail.setText(e.getMessage());
            return;
        } catch (PhoneNumberNotValidException e) {
            lbErrPhoneNumber.setText(e.getMessage());
            return;
        } catch (IsExistedException e) {
           if(e.getMessage().equals("*Số điện thoại đã tồn tại trong hệ thống")){
               lbErrPhoneNumber.setText(e.getMessage());
                return;
           }

           if(e.getMessage().equals("*Email đã tồn tại trong hệ thống")){
               lbErrEmail.setText(e.getMessage());
               return;
           }
        }
        tbCustomer.setItems(customerService.customersData());
        customerService.clear(txtEmail,txtId,txtName,txtPhoneNumber);

    }



    public void search(KeyEvent keyEvent) throws SQLException { // tim kiem khach hang
        String keyword = txtSearch.getText();
        tbCustomer.setItems(customerService.search(keyword, customerService.customersData()));
    }


    public void onClickHistory(ActionEvent actionEvent) throws IOException {
        App.setRootPop("/com/example/QuanLyMuonSachFrm","Danh sách đã mượn", false, Optional.empty());
    }

    public void onClickRefresh(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
    }
}
