package com.example.Helper;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

public class TableHelper { // hien thi cac du lieu len bang
        @SafeVarargs
        public static <T> void showOnTable(TableView<T> table, ObservableList<T> data, TableColumn<T, ?>... columns) {
            table.setItems(data);
            table.getColumns().setAll(columns);
        }
}
