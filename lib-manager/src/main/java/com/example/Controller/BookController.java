package com.example.Controller;

import com.example.DTO.BookDTO;

import com.example.Helper.AlertHelper;
import com.example.Service.BookService;
import com.example.Helper.TableHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    public TextField txtSearch;
    @FXML
    private ComboBox<String> cbAuthor;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtCategory;
    @FXML
    private Button btnAddCategory;
    @FXML
    private Button btnAddAuthor;
    @FXML
    private Button btnUpdate, btnAdd;
    @FXML
    private Button btnDelete, btnExport;
    @FXML
    private RadioButton rAuthor;
    @FXML
    private RadioButton rCategory;
    @FXML
    private ComboBox<String> cbFilter;
    @FXML
    private RadioButton rNone;
    @FXML
    private TextField txtIdBook;

    @FXML
    private TextField txtNameBook;

    @FXML
    private DatePicker dpPublishDate;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TableView<BookDTO> tbBook;
    @FXML
    private TableColumn<BookDTO, Integer> colId;
    @FXML
    private TableColumn<BookDTO, String> colBookName;
    @FXML
    private TableColumn<BookDTO, String> colAuthorName;
    @FXML
    private TableColumn<BookDTO, String> colCategoryName;
    @FXML
    private TableColumn<BookDTO, LocalDate> colPublishDate;
    @FXML
    private TableColumn<BookDTO, Integer> colQuantity;

    private final BookService bookService;

    private boolean flag = false;
    private boolean flag1 = false;

    public BookController() { //contructor
        this.bookService = new BookService(); // dependcy injection // tiem phu thuoc
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // phuong thuc khoi tao
        setCell();
        try {
            cbAuthor.setItems(bookService.listAuthor());
            cbCategory.setItems(bookService.listCategory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cbFilter.setValue("<None>");

        try {
            TableHelper.showOnTable(
                    tbBook,
                    bookService.getBookData(),
                    colId,
                    colBookName,
                    colAuthorName,
                    colCategoryName,
                    colPublishDate,
                    colQuantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    private void setCell() { // co dinh gia tri cho moi cot
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colPublishDate.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void onSelected(MouseEvent mouseEvent) { // khi minh chon 1 dong tren bang
        BookDTO selectedBook = tbBook.getSelectionModel().getSelectedItem(); // lay ra doi tuong minh chon tren bang

        if (selectedBook != null) {
            String bookIdStr = String.valueOf(selectedBook.getBookId());
            String quantityStr = String.valueOf(selectedBook.getQuantity());
            txtIdBook.setText(bookIdStr);
            txtNameBook.setText(selectedBook.getBookName());
            txtQuantity.setText(quantityStr);
            cbCategory.setValue(selectedBook.getCategoryName());
            cbAuthor.setValue(selectedBook.getAuthorName());
            dpPublishDate.setValue(selectedBook.getPublishDate());
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws SQLException { // nut them sach
        int quantityInt = Integer.parseInt(txtQuantity.getText());
        bookService.addNewBook(
                txtIdBook.getText(),
                txtNameBook.getText(),
                txtAuthor.getText(),
                cbAuthor.getValue(),
                txtCategory.getText(),
                cbCategory.getValue(),
                quantityInt,
                dpPublishDate.getValue());

        tbBook.setItems(bookService.getBookData());
        clear();

    }

    public void onClickDelete(ActionEvent actionEvent) throws SQLException { // nut xoa sach
        BookDTO bookDTOSelected = tbBook.getSelectionModel().getSelectedItem();
        if (bookDTOSelected == null) {
        } else {

            bookService.deleteBook(bookDTOSelected.getBookId());
            tbBook.setItems(BookService.getBookData());
        }
        clear();

    }




    public void onClickUpdate(ActionEvent actionEvent) throws SQLException { // nut cap nhap (update tai day)
        if (validateInput()) { // kiem tra xem da chon sach nao de cap nhat chua
            BookDTO selectedBook = tbBook.getSelectionModel().getSelectedItem();
            if (selectedBook == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Chọn một cuốn sách để cập nhật.");
                return;
            }

            String selectedBook_id = selectedBook.getBookId(); // lay ra id sach muon cap nhat
            System.out.println("book_id: " + selectedBook_id);

            int Id = bookService.findIdByName("id", selectedBook_id, "book", "book_id");
            System.out.println("id: " + Id);

            String newBookId = txtIdBook.getText();
            String newBookName = txtNameBook.getText();
            String newAuthorName = cbAuthor.getValue();
            String newCategoryName = cbCategory.getValue();
            int newQuantity = Integer.parseInt(txtQuantity.getText());
            LocalDate newPublishDate = dpPublishDate.getValue();

            bookService.updateBook(Id, newBookId, newBookName, newAuthorName, newCategoryName, newQuantity,
                    newPublishDate);

            tbBook.setItems(bookService.getBookData());

            // Show a success message
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật sách thành công");

            clear();

        }
    }




    public void onClickAddAuthor(ActionEvent actionEvent) { // nut them tac gia
        bookService.toggleVisibilityAndButton(
                btnAddAuthor,
                flag,
                cbAuthor,
                txtAuthor,
                "Thêm tác giả",
                "Huỷ",
                "Chọn tác giả"
        );
        flag = !flag;
    }

    public void onClickAddCategory(ActionEvent actionEvent) { // nut them the loai
        bookService.toggleVisibilityAndButton(
                btnAddCategory,
                flag1,
                cbCategory,
                txtCategory,
                "Thêm thể loại",
                "Huỷ",
                "Chọn thể loại"
        );
        flag1 = !flag1;
    }

    private boolean validateInput() { // xac thuc du lieu nhap vao
        if (txtIdBook.getText().isEmpty() || txtNameBook.getText().isEmpty() || txtQuantity.getText().isEmpty() || cbAuthor.getValue() == null || cbCategory.getValue() == null || dpPublishDate.getValue() == null) { // kiem tra xem o nao thieu thi bao loi
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng điền đầy đủ thông tin.");
            return false;
        }

        try {
            int quantityInt = Integer.parseInt(txtQuantity.getText()); // kiem tra so luong > 0
            if (quantityInt <= 0) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng phải lớn hơn 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng phải là một số nguyên dương.");
            return false;
        }


        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dpPublishDate.getValue();

        if (selectedDate.isAfter(currentDate)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Ngày xuất bản không thể ở tương lai.");
            return false;
        }

        if (selectedDate.isBefore(LocalDate.of(1900, 1, 1))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Ngày xuất bản không hợp lệ.");
            return false;
        }

        return true;

    }

    public void search(KeyEvent keyEvent) throws SQLException { // tim kiem sach
        String keyword = txtSearch.getText();
        tbBook.setItems(bookService.search(keyword, bookService.getBookData()));
    }

    public void onFilterSelected(ActionEvent actionEvent) throws SQLException { // chon danh muc tim kiem
        String filter = cbFilter.getValue();
        if (filter == null) {
            return;
        }

        String filterType = rAuthor.isSelected() ? "author" : rCategory.isSelected() ? "category" : "";

        if (!filterType.isEmpty()) {
            tbBook.setItems(bookService.filter(filter, filterType));
        } else {


            tbBook.setItems(bookService.getBookData());
        }
    }

    public void getListForFilter(ActionEvent actionEvent) throws SQLException { // lay danh sach de loc, radio button
        if (rAuthor.isSelected()) { // radio button tac author dc chon
            cbFilter.setItems(bookService.listAuthor());
        } else if (rCategory.isSelected()) {
            cbFilter.setItems(bookService.listCategory());
        } else {
            tbBook.setItems(bookService.getBookData());
            cbFilter.setItems(null);
            cbFilter.setPromptText("<None>");
        }
    }



    private void clear() {
        txtIdBook.setText("");
        txtNameBook.setText("");
        txtQuantity.setText("");
        txtCategory.setText("");
        txtAuthor.setText("");
        txtCategory.setPromptText("<None>");
        cbAuthor.setValue(null);
        cbCategory.setValue(null);
        dpPublishDate.setValue(null);
    }
}

