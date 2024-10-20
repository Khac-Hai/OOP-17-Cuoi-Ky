package com.example.Service;

import com.example.Common.Regex;
import com.example.Exception.EmailNotValidException;
import com.example.Exception.IsExistedException;
import com.example.Exception.NullException;
import com.example.Exception.PhoneNumberNotValidException;
import com.example.Models.Customer;
import com.example.Helper.AlertHelper;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerService {
    Stage stage = new Stage();
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    public ObservableList<Customer> customersData() throws SQLException {
        ObservableList<Customer> result = FXCollections.observableArrayList();

        String getCustomerSql="Select * from customer where is_delete = '" + "0'";
        ResultSet resultSet = executeQuery.executeQuery(getCustomerSql);

        while (resultSet.next()){
            Customer customer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            result.add(customer);
        }

        return result;
    }

    public void clear(Control... controls) {
        for (Control control : controls) {
            if (control instanceof TextField) {
                ((TextField) control).clear();
            } else if (control instanceof Label) {
                ((Label) control).setText("");
            }
        }
    }
    public void addCustomer(String customerId, String name, String email, String phoneNumber) throws EmailNotValidException, PhoneNumberNotValidException, NullException, SQLException, IsExistedException { // them khach hang vao csdl
        validateInformation(customerId, name, email, phoneNumber); // kiem tra thong tin

        String addCustomerSql = "INSERT INTO customer (identity_card,name, email, phone_number) VALUES ('" +
                customerId + "', '" +
                name + "', '" +
                email + "', '" +
                phoneNumber + "')";

        executeQuery.executeUpdate(addCustomerSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm khách hàng mới thành công!"); // hien thi thong bao
    }

    public void updateCustomer(String customerIdOld, String name, String email, String phoneNumber, String customerIdNew) throws PhoneNumberNotValidException, EmailNotValidException, NullException, IsExistedException, SQLException { // cap nhat thong tin khach hang
        regexInfo(name, email, phoneNumber);
        if(isExisted("email",email) && !email.equals(getData("email",email))){
            throw new IsExistedException("Email đã tồn tại trong hệ thống");
        }
        if(isExisted("phone_number", phoneNumber) && phoneNumber.equals(getData("phone_number", phoneNumber))){
            throw new IsExistedException("Số điện thoại đã tồn tại trong hệ thống");
        }

        String updateCustomerSql = "UPDATE customer "
                + "SET name = '" + name + "', "
                + "identity_card = '" + customerIdNew + "', "
                + "email = '" + email + "', "
                + "phone_number = '" + phoneNumber + "' "
                + "WHERE identity_card = '" + customerIdOld + "'";

        executeQuery.executeUpdate(updateCustomerSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật thành công!");

    }

    public void deleteCustomer(String customerId){ // xoa mem khach hang
        String deleteCustomerSql = "UPDATE customer "
                + "SET is_delete = 1 "
                + "WHERE identity_card = '" + customerId + "'";

        if(AlertHelper.showConfirmation("Việc xoá này sẽ xoá toàn bộ lịch sử mượn của khách!")){ // hien thi xac nhan xoa
            executeQuery.executeUpdate(deleteCustomerSql);
        }
    }


    private void validateInformation(String customerId, String name, String email, String phoneNumber) throws NullException, EmailNotValidException, PhoneNumberNotValidException, SQLException, IsExistedException { // kiem tra xem ton tai hay chua
        regexInfo(name, email, phoneNumber);

        if(isExisted("identity_card",customerId)){
            throw new IsExistedException("*Id đã tồn tại trong hệ thống");
        }

        if(isExisted("phone_number",phoneNumber)) {
            throw new IsExistedException("*Số điện thoại đã tồn tại trong hệ thống");
        }

        if(isExisted("email", email)){
            throw new IsExistedException("*Email đã tồn tại trong hệ thống");
        }

    }

    private void regexInfo(String name, String email, String phoneNumber) throws NullException, EmailNotValidException, PhoneNumberNotValidException { // check hop le email voi sdt
        if(name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){
            throw new NullException("Không được để trống các thông tin");
        }

        if(!isValidEmail(email)){
            throw new EmailNotValidException("Email không hợp lệ");
        }

        if(!isValidPhoneNumber(phoneNumber)){
            throw new PhoneNumberNotValidException("Số đện thoại không hợp lệ");
        }
    }




    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(Regex.rgEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile(Regex.rgPhoneNumber);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    public FilteredList<Customer> search(String keyword, ObservableList<Customer> customers) {
        FilteredList<Customer> filteredData = new FilteredList<>(customers, p -> true);
        if (keyword.isEmpty()) {
            return filteredData;
        } else {
            String lowerCaseKeyword = keyword.toLowerCase();

            filteredData.setPredicate(customer -> {
                String customerId = customer.getIdentityCard().toLowerCase();
                String email = customer.getEmail().toLowerCase();
                String identityCard = customer.getIdentityCard().toLowerCase();
                String phoneNumber = customer.getPhoneNumber().toLowerCase();
                String name = customer.getName().toLowerCase();

                return email.contains(lowerCaseKeyword) || customerId.contains(lowerCaseKeyword)
                        || identityCard.contains(lowerCaseKeyword)
                        || phoneNumber.contains(lowerCaseKeyword)
                        || name.contains(lowerCaseKeyword);
            });

            return filteredData;
        }
    }

    private boolean isExisted(String key, String value) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM customer WHERE " + key + " = '" + value + "'" + "And is_delete = '0'";
        ResultSet resultSet = executeQuery.executeQuery(checkExistSql);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

    public void setTextFieldClearErrorOnTyping(TextField textField, Label errorLabel) {
        textField.setOnKeyTyped(event -> clear(errorLabel));
    }

    private String getData(String key, String value) throws SQLException {
       String sqlGetData = "Select " + key + " from customer where " + key + "='" + value +"'";
       ResultSet resultSet = executeQuery.executeQuery(sqlGetData);

        if (resultSet.next()) {
            return resultSet.getString(key);
        }

        return null;

    }
}
