package sample;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import db.DBManager;
import db.entity.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

public class AdminFormController {
    @FXML
    private TableView<?> tableScience;

    @FXML
    private TableColumn<?, ?> scienceColumn;

    @FXML
    private Button buttonScienceDel;

    @FXML
    private Button buttonScienceChange;

    @FXML
    private Button buttonScienceCreate;

    @FXML
    private TextField textScience;

    @FXML
    private TableView<Department> tableDepartment;

    @FXML
    private TableColumn<Department, String> depColumn;

    @FXML
    private Button buttonChangeDep;

    @FXML
    private Button buttonDeleteDep;

    @FXML
    private Button buttonAddDep;

    @FXML
    private TextField textDep;

    @FXML
    private TableView<?> tableSpeciality;

    @FXML
    private TableColumn<?, ?> specNameColumn;

    @FXML
    private TableColumn<?, ?> scienceNameColumn;

    @FXML
    private Button buttonSpecialityDel;

    @FXML
    private Button buttonSpecialityChange;

    @FXML
    private Button buttonSpecialityAdd;

    @FXML
    private TextField textSpeciality;

    @FXML
    private ComboBox<?> Science_select;

    @FXML
    void initialize(){
        showAllDep();
        buttonDeleteDep.setOnAction(event -> {
            deleteDep();
            showAllDep();
        });
        buttonChangeDep.setOnAction(event -> {
            if (textDep.getText() != "")
                updateDep();
            showAllDep();
        });
        buttonAddDep.setOnAction(event -> {
            if (textDep.getText() != "")
                addDep();
            showAllDep();
        });

        depColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


    }


    private void showAllDep(){
        DBManager manager = DBManager.getInstance();
        ObservableList<Department> departments = FXCollections
                .observableArrayList(manager.getDepartment());
        tableDepartment.setItems(departments);
    }

    private void deleteDep(){
        DBManager manager = DBManager.getInstance();
        if (tableDepartment.getSelectionModel().getSelectedItem() != null)
            manager.deleteDepartment(tableDepartment.getSelectionModel().getSelectedItem());
    }
    private void updateDep(){
        DBManager manager = DBManager.getInstance();
        if (tableDepartment.getSelectionModel().getSelectedItem() != null)
            manager.updateDepartment(textDep.getText(),tableDepartment.getSelectionModel().getSelectedItem());
    }
    private void addDep(){
        DBManager manager = DBManager.getInstance();
        manager.insertDepartment(textDep.getText());
    }



}

//import static com.postgressql.jdbc.StringUtils.isNullOrEmpty;
