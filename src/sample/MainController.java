package sample;

import db.DBManager;
import db.entity.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class MainController {

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
    private ComboBox<ScienceType> DB_fieldOfKnowledge;
    @FXML
    private ComboBox<Specialty> DB_spec;
    @FXML
    private ComboBox<String> DB_eduProgram;
    @FXML
    private ComboBox<String> eduForm;
    @FXML
    private DatePicker startOfEduDate;
    @FXML
    private TextField qualification;
    @FXML
    private TextField eduPeriod;
    @FXML
    private TextField dependsOn;
    @FXML
    private ComboBox<Department> DB_department;

    // ГРАФИК ОБУЧ ПРОЦЕССА
    @FXML
    private TableView<TableData> view;
    @FXML
    private TableColumn<TableData, String> twCourse;
    @FXML
    private TableColumn<TableData, String> tw1;
    @FXML
    private TableColumn<TableData, String> tw2;
    @FXML
    private TableColumn<TableData, String> tw3;
    @FXML
    private TableColumn<TableData, String> tw4;
    @FXML
    private TableColumn<TableData, String> tw5;
    @FXML
    private TableColumn<TableData, String> tw6;
    @FXML
    private TableColumn<TableData, String> tw7;
    @FXML
    private TableColumn<TableData, String> tw8;
    @FXML
    private TableColumn<TableData, String> tw9;
    @FXML
    private TableColumn<TableData, String> tw10;
    @FXML
    private TableColumn<TableData, String> tw11;
    @FXML
    private TableColumn<TableData, String> tw12;
    @FXML
    private TableColumn<TableData, String> tw13;
    @FXML
    private TableColumn<TableData, String> tw14;
    @FXML
    private TableColumn<TableData, String> tw15;
    @FXML
    private TableColumn<TableData, String> tw16;
    @FXML
    private TableColumn<TableData, String> tw17;
    @FXML
    private TableColumn<TableData, String> tw18;
    @FXML
    private TableColumn<TableData, String> tw19;
    @FXML
    private TableColumn<TableData, String> tw20;
    @FXML
    private TableColumn<TableData, String> tw21;
    @FXML
    private TableColumn<TableData, String> tw22;
    @FXML
    private TableColumn<TableData, String> tw23;
    @FXML
    private TableColumn<TableData, String> tw24;
    @FXML
    private TableColumn<TableData, String> tw25;
    @FXML
    private TableColumn<TableData, String> tw26;
    @FXML
    private TableColumn<TableData, String> tw27;
    @FXML
    private TableColumn<TableData, String> tw28;
    @FXML
    private TableColumn<TableData, String> tw29;
    @FXML
    private TableColumn<TableData, String> tw30;
    @FXML
    private TableColumn<TableData, String> tw31;
    @FXML
    private TableColumn<TableData, String> tw32;
    @FXML
    private TableColumn<TableData, String> tw33;
    @FXML
    private TableColumn<TableData, String> tw34;
    @FXML
    private TableColumn<TableData, String> tw35;
    @FXML
    private TableColumn<TableData, String> tw36;
    @FXML
    private TableColumn<TableData, String> tw37;
    @FXML
    private TableColumn<TableData, String> tw38;
    @FXML
    private TableColumn<TableData, String> tw39;
    @FXML
    private TableColumn<TableData, String> tw40;
    @FXML
    private TableColumn<TableData, String> tw41;
    @FXML
    private TableColumn<TableData, String> tw42;
    @FXML
    private TableColumn<TableData, String> tw43;
    @FXML
    private TableColumn<TableData, String> tw44;
    @FXML
    private TableColumn<TableData, String> tw45;
    @FXML
    private TableColumn<TableData, String> tw46;
    @FXML
    private TableColumn<TableData, String> tw47;
    @FXML
    private TableColumn<TableData, String> tw48;
    @FXML
    private TableColumn<TableData, String> tw49;
    @FXML
    private TableColumn<TableData, String> tw50;
    @FXML
    private TableColumn<TableData, String> tw51;
    @FXML
    private TableColumn<TableData, String> tw52;

    @FXML
    private MenuItem openExcel;

    @FXML
    private MenuItem saveExcel;

    @FXML
    void initialize() {
        // prevent table columns interaction
        for (TableColumn<?, ?> column : view.getColumns()) {
            column.setResizable(false);
            column.impl_setReorderable(false);
            column.setSortable(false);
        }

        try {
            java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
            int index = 1;
            for (java.lang.reflect.Field field : fields) {
                if (field.getName().equals("tw" + index)) {
                    ((TableColumn<?, ?>) field.get(this))
                                .setCellValueFactory(new PropertyValueFactory<>("tw" + index));
                    ++index;
                }
            }
            twCourse.setCellValueFactory(new PropertyValueFactory<>("twCourse"));
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            throw new RuntimeException(ex);
        }

        eduForm.setItems(FXCollections.observableArrayList("Денна", "Заочна"));

        DBManager dbManager = DBManager.getInstance();

        DB_department.setItems(FXCollections.observableArrayList(dbManager.getAllDepartments()));
        DB_fieldOfKnowledge
                    .setItems(FXCollections.observableArrayList(dbManager.getAllScienceTypes()));
        DB_spec.setItems(FXCollections.observableArrayList(dbManager.getAllSpecialties()));
        for (Specialty specialty : DB_spec.getItems()) {
            DB_eduProgram.getItems()
                        .add(specialty.getName().substring(specialty.getName().indexOf('\"') + 1,
                                    specialty.getName().lastIndexOf('\"')));
        }

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
        setTableData(manager.getExcelController().getData());
    }

    private void exportInExcel() throws IOException {
        DBManager manager = DBManager.getInstance();
        manager.exportInExcel();
    }

    private void setTableData(String[] data) {
        try {
            view.getItems().clear();
            for (int course = 0; course < 4; ++course) {
                view.getItems()
                            .add(new TableData(
                                        Arrays.copyOfRange(data, 0 + course * 52, 52 + course * 52),
                                        String.valueOf(course + 1)));
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                    | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static class TableData {
        private String twCourse;
        private String tw1;
        private String tw2;
        private String tw3;
        private String tw4;
        private String tw5;
        private String tw6;
        private String tw7;
        private String tw8;
        private String tw9;
        private String tw10;
        private String tw11;
        private String tw12;
        private String tw13;
        private String tw14;
        private String tw15;
        private String tw16;
        private String tw17;
        private String tw18;
        private String tw19;
        private String tw20;
        private String tw21;
        private String tw22;
        private String tw23;
        private String tw24;
        private String tw25;
        private String tw26;
        private String tw27;
        private String tw28;
        private String tw29;
        private String tw30;
        private String tw31;
        private String tw32;
        private String tw33;
        private String tw34;
        private String tw35;
        private String tw36;
        private String tw37;
        private String tw38;
        private String tw39;
        private String tw40;
        private String tw41;
        private String tw42;
        private String tw43;
        private String tw44;
        private String tw45;
        private String tw46;
        private String tw47;
        private String tw48;
        private String tw49;
        private String tw50;
        private String tw51;
        private String tw52;

        public TableData(String[] data, String course) throws IllegalArgumentException,
                    IllegalAccessException, NoSuchFieldException, SecurityException {
            final int SIZE = 52;
            if (data == null || data.length != SIZE) {
                throw new IllegalArgumentException("data.length must be 52");
            }
            for (int index = 1; index <= SIZE; ++index) {
                this.getClass().getDeclaredField("tw" + index).set(this, data[index - 1]);
            }
            this.twCourse = course;
        }

        public String getTwCourse() {
            return twCourse;
        }

        public String getTw1() {
            return tw1;
        }

        public String getTw2() {
            return tw2;
        }

        public String getTw3() {
            return tw3;
        }

        public String getTw4() {
            return tw4;
        }

        public String getTw5() {
            return tw5;
        }

        public String getTw6() {
            return tw6;
        }

        public String getTw7() {
            return tw7;
        }

        public String getTw8() {
            return tw8;
        }

        public String getTw9() {
            return tw9;
        }

        public String getTw10() {
            return tw10;
        }

        public String getTw11() {
            return tw11;
        }

        public String getTw12() {
            return tw12;
        }

        public String getTw13() {
            return tw13;
        }

        public String getTw14() {
            return tw14;
        }

        public String getTw15() {
            return tw15;
        }

        public String getTw16() {
            return tw16;
        }

        public String getTw17() {
            return tw17;
        }

        public String getTw18() {
            return tw18;
        }

        public String getTw19() {
            return tw19;
        }

        public String getTw20() {
            return tw20;
        }

        public String getTw21() {
            return tw21;
        }

        public String getTw22() {
            return tw22;
        }

        public String getTw23() {
            return tw23;
        }

        public String getTw24() {
            return tw24;
        }

        public String getTw25() {
            return tw25;
        }

        public String getTw26() {
            return tw26;
        }

        public String getTw27() {
            return tw27;
        }

        public String getTw28() {
            return tw28;
        }

        public String getTw29() {
            return tw29;
        }

        public String getTw30() {
            return tw30;
        }

        public String getTw31() {
            return tw31;
        }

        public String getTw32() {
            return tw32;
        }

        public String getTw33() {
            return tw33;
        }

        public String getTw34() {
            return tw34;
        }

        public String getTw35() {
            return tw35;
        }

        public String getTw36() {
            return tw36;
        }

        public String getTw37() {
            return tw37;
        }

        public String getTw38() {
            return tw38;
        }

        public String getTw39() {
            return tw39;
        }

        public String getTw40() {
            return tw40;
        }

        public String getTw41() {
            return tw41;
        }

        public String getTw42() {
            return tw42;
        }

        public String getTw43() {
            return tw43;
        }

        public String getTw44() {
            return tw44;
        }

        public String getTw45() {
            return tw45;
        }

        public String getTw46() {
            return tw46;
        }

        public String getTw47() {
            return tw47;
        }

        public String getTw48() {
            return tw48;
        }

        public String getTw49() {
            return tw49;
        }

        public String getTw50() {
            return tw50;
        }

        public String getTw51() {
            return tw51;
        }

        public String getTw52() {
            return tw52;
        }

        public void setTwCourse(String twCourse) {
            this.twCourse = twCourse;
        }

        public void setTw1(String tw1) {
            this.tw1 = tw1;
        }

        public void setTw2(String tw2) {
            this.tw2 = tw2;
        }

        public void setTw3(String tw3) {
            this.tw3 = tw3;
        }

        public void setTw4(String tw4) {
            this.tw4 = tw4;
        }

        public void setTw5(String tw5) {
            this.tw5 = tw5;
        }

        public void setTw6(String tw6) {
            this.tw6 = tw6;
        }

        public void setTw7(String tw7) {
            this.tw7 = tw7;
        }

        public void setTw8(String tw8) {
            this.tw8 = tw8;
        }

        public void setTw9(String tw9) {
            this.tw9 = tw9;
        }

        public void setTw10(String tw10) {
            this.tw10 = tw10;
        }

        public void setTw11(String tw11) {
            this.tw11 = tw11;
        }

        public void setTw12(String tw12) {
            this.tw12 = tw12;
        }

        public void setTw13(String tw13) {
            this.tw13 = tw13;
        }

        public void setTw14(String tw14) {
            this.tw14 = tw14;
        }

        public void setTw15(String tw15) {
            this.tw15 = tw15;
        }

        public void setTw16(String tw16) {
            this.tw16 = tw16;
        }

        public void setTw17(String tw17) {
            this.tw17 = tw17;
        }

        public void setTw18(String tw18) {
            this.tw18 = tw18;
        }

        public void setTw19(String tw19) {
            this.tw19 = tw19;
        }

        public void setTw20(String tw20) {
            this.tw20 = tw20;
        }

        public void setTw21(String tw21) {
            this.tw21 = tw21;
        }

        public void setTw22(String tw22) {
            this.tw22 = tw22;
        }

        public void setTw23(String tw23) {
            this.tw23 = tw23;
        }

        public void setTw24(String tw24) {
            this.tw24 = tw24;
        }

        public void setTw25(String tw25) {
            this.tw25 = tw25;
        }

        public void setTw26(String tw26) {
            this.tw26 = tw26;
        }

        public void setTw27(String tw27) {
            this.tw27 = tw27;
        }

        public void setTw28(String tw28) {
            this.tw28 = tw28;
        }

        public void setTw29(String tw29) {
            this.tw29 = tw29;
        }

        public void setTw30(String tw30) {
            this.tw30 = tw30;
        }

        public void setTw31(String tw31) {
            this.tw31 = tw31;
        }

        public void setTw32(String tw32) {
            this.tw32 = tw32;
        }

        public void setTw33(String tw33) {
            this.tw33 = tw33;
        }

        public void setTw34(String tw34) {
            this.tw34 = tw34;
        }

        public void setTw35(String tw35) {
            this.tw35 = tw35;
        }

        public void setTw36(String tw36) {
            this.tw36 = tw36;
        }

        public void setTw37(String tw37) {
            this.tw37 = tw37;
        }

        public void setTw38(String tw38) {
            this.tw38 = tw38;
        }

        public void setTw39(String tw39) {
            this.tw39 = tw39;
        }

        public void setTw40(String tw40) {
            this.tw40 = tw40;
        }

        public void setTw41(String tw41) {
            this.tw41 = tw41;
        }

        public void setTw42(String tw42) {
            this.tw42 = tw42;
        }

        public void setTw43(String tw43) {
            this.tw43 = tw43;
        }

        public void setTw44(String tw44) {
            this.tw44 = tw44;
        }

        public void setTw45(String tw45) {
            this.tw45 = tw45;
        }

        public void setTw46(String tw46) {
            this.tw46 = tw46;
        }

        public void setTw47(String tw47) {
            this.tw47 = tw47;
        }

        public void setTw48(String tw48) {
            this.tw48 = tw48;
        }

        public void setTw49(String tw49) {
            this.tw49 = tw49;
        }

        public void setTw50(String tw50) {
            this.tw50 = tw50;
        }

        public void setTw51(String tw51) {
            this.tw51 = tw51;
        }

        public void setTw52(String tw52) {
            this.tw52 = tw52;
        }
    }
}
