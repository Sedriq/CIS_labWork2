package sample;

import db.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class MainController  {

    @FXML
    private TextField name;
    @FXML
    private TextField protocol;
    @FXML
    private DatePicker protocolCreationDate;
    @FXML
    private TextField decree;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker decreeDate;
    @FXML
    private TextField degree;
    @FXML
    private ComboBox DB_fieldOfKnowledge;
    @FXML
    private ComboBox DB_spec;
    @FXML
    private ComboBox DB_eduProgram;
    @FXML
    private ComboBox eduForm;
    @FXML
    private DatePicker startOfEduDate;
    @FXML
    private TextField qualification;
    @FXML
    private TextField eduPeriod;
    @FXML
    private TextField dependsOn;
    @FXML
    private ComboBox DB_department;

    //ГРАФИК ОБУЧ ПРОЦЕССА
    @FXML
    private TableView<String> view;
    @FXML
    private TableColumn<String, String> twCourse;
    @FXML
    private TableColumn<String, String> tw1;
    @FXML
    private TableColumn<String, String> tw2;
    @FXML
    private TableColumn<String, String> tw3;
    @FXML
    private TableColumn<String, String> tw4;
    @FXML
    private TableColumn<String, String> tw5;
    @FXML
    private TableColumn<String, String> tw6;
    @FXML
    private TableColumn<String, String> tw7;
    @FXML
    private TableColumn<String, String> tw8;
    @FXML
    private TableColumn<String, String> tw9;
    @FXML
    private TableColumn<String, String> tw10;
    @FXML
    private TableColumn<String, String> tw11;
    @FXML
    private TableColumn<String, String> tw12;
    @FXML
    private TableColumn<String, String> tw13;
    @FXML
    private TableColumn<String, String> tw14;
    @FXML
    private TableColumn<String, String> tw15;
    @FXML
    private TableColumn<String, String> tw16;
    @FXML
    private TableColumn<String, String> tw17;
    @FXML
    private TableColumn<String, String> tw18;
    @FXML
    private TableColumn<String, String> tw19;
    @FXML
    private TableColumn<String, String> tw20;
    @FXML
    private TableColumn<String, String> tw21;
    @FXML
    private TableColumn<String, String> tw22;
    @FXML
    private TableColumn<String, String> tw23;
    @FXML
    private TableColumn<String, String> tw24;
    @FXML
    private TableColumn<String, String> tw25;
    @FXML
    private TableColumn<String, String> tw26;
    @FXML
    private TableColumn<String, String> tw27;
    @FXML
    private TableColumn<String, String> tw28;
    @FXML
    private TableColumn<String, String> tw29;
    @FXML
    private TableColumn<String, String> tw30;
    @FXML
    private TableColumn<String, String> tw31;
    @FXML
    private TableColumn<String, String> tw32;
    @FXML
    private TableColumn<String, String> tw33;
    @FXML
    private TableColumn<String, String> tw34;
    @FXML
    private TableColumn<String, String> tw35;
    @FXML
    private TableColumn<String, String> tw36;
    @FXML
    private TableColumn<String, String> tw37;
    @FXML
    private TableColumn<String, String> tw38;
    @FXML
    private TableColumn<String, String> tw39;
    @FXML
    private TableColumn<String, String> tw40;
    @FXML
    private TableColumn<String, String> tw41;
    @FXML
    private TableColumn<String, String> tw42;
    @FXML
    private TableColumn<String, String> tw43;
    @FXML
    private TableColumn<String, String> tw44;
    @FXML
    private TableColumn<String, String> tw45;
    @FXML
    private TableColumn<String, String> tw46;
    @FXML
    private TableColumn<String, String> tw47;
    @FXML
    private TableColumn<String, String> tw48;
    @FXML
    private TableColumn<String, String> tw49;
    @FXML
    private TableColumn<String, String> tw50;
    @FXML
    private TableColumn<String, String> tw51;
    @FXML
    private TableColumn<String, String> tw52;

    @FXML
    private MenuItem openExcel;

    @FXML
    private MenuItem saveExcel;

    @FXML
    void initialize() {
        openExcel.setOnAction((event -> {
            try {
                importFromExcel();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }));

        saveExcel.setOnAction((event -> {
            try {
                exportInExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private void importFromExcel() throws IOException, SQLException {
        DBManager manager = DBManager.getInstance();
        manager.importFromExcel();
    }

    private void exportInExcel() throws IOException {
        DBManager manager = DBManager.getInstance();
        manager.exportInExcel();
    }
}
