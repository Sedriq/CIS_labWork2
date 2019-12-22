package sample;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExcelController {
    String data[] = null;

    public String[] getData() {
        return data;
    }
    
    public void importDataFromExcel() throws IOException, SQLException {
        readFile();
    }
    public void exportDataInExcel() throws IOException {
        saveFile();
    }

    private void saveFile() throws IOException {
        if (data != null) {
            FileInputStream fis = new FileInputStream("./Title.xls");

            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            addDataInExcel(sheet);
            addHoursInExcel(sheet);

            fis.close();

            FileOutputStream outputStream = new FileOutputStream("./Title.xls");
            wb.write(outputStream);

            wb.close();
            outputStream.close();
            alertInfo("File successfully saved!");
        } else {
            alertWarning("There is nothing to save!\nPlease, open a file with data.");
        }
    }

    private void readFile() throws IOException, SQLException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.xls", "*.xls"),
                new FileChooser.ExtensionFilter("*.xlsx", "*.xlsx")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String extension = fileExtension(selectedFile);
            FileInputStream fis = new FileInputStream(selectedFile);

            if (extension.equals("xls")) {
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                HSSFSheet sheet = wb.getSheetAt(0);
                data = new String[52 * 4];
                addDataInArray(sheet);
                fis.close();
                wb.close();
            } else {
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet sheet = wb.getSheetAt(0);
                data = new String[52 * 4];
                addDataInArray(sheet);
                fis.close();
                wb.close();
            }
            alertInfo("File successfully opened!");
        }
    }

    private static String fileExtension(File file) {
        String name = file.getName();
        if(name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
            return name.substring(name.lastIndexOf(".") + 1);
        else
            return "";
    }

    private  void addDataInArray(Sheet sheet) {
        int rowIndex = 15;
        int j = 0;
        while (rowIndex <= 18) {
            Row repeated = sheet.getRow(12);
            Row row = sheet.getRow(rowIndex);
            DataFormatter df = new DataFormatter();

            for (int i = 1; i < row.getLastCellNum(); i++, j++) {
                Cell rCell = repeated.getCell(i); //From the 2 similar cells will be written data from the first one
                if (df.formatCellValue(rCell).equals(df.formatCellValue(repeated.getCell(i - 1)))) { // troubles with similar cells in advance.
                    j--;
                    continue;
                }
                Cell cell = row.getCell(i);
                data[j] = cell.getStringCellValue();
            }
            rowIndex++;
        }
    }

    private void addHoursInExcel(Sheet sheet) {
        int rowIndex = 32, from = 0, to = 52;
        while (rowIndex < 36) {
            ArrayList<String> getH = new ArrayList<>();
            ArrayList<String> getC = new ArrayList<>();
            ArrayList<String> getK = new ArrayList<>();
            ArrayList<String> getA = new ArrayList<>();

            String[] copiedArr = Arrays.copyOfRange(data, from, to);

            for (int i = from; i < to; i++) {
                if (data[i].equals("Н"))
                    getH.add(data[i]);
                if (data[i].equals("С"))
                    getC.add(data[i]);
                if (data[i].equals("К"))
                    getK.add(data[i]);
                if (data[i].equals("А"))
                    getA.add(data[i]);
            }

            int arrOfNumbers[] = {getH.size() + getC.size(), 0, 0, 0, getK.size(), 0};
            int theoryAmount = copiedArr.length - getH.size() - getC.size() - getK.size() - getA.size();
            Row row = sheet.getRow(rowIndex);

            for (int i = 3, j = 0; i <= 18; i += 3, j++) {
                Cell cell = row.getCell(i);
                if (i == 3 && getA.size() > 0) {
                    cell.setCellValue(theoryAmount - 8);
                    theoryAmount = arrOfNumbers[j];
                }
                else if (i == 12 && getA.size() > 0) {
                    cell.setCellValue(getA.size());
                    theoryAmount = arrOfNumbers[j];
                }
                else {
                    cell.setCellValue(theoryAmount);
                    theoryAmount = arrOfNumbers[j];
                }
            }

            from += 52;
            to += 52;
            rowIndex++;
        }
    }

    private  void addDataInExcel(Sheet sheet) {
        int rowIndex = 24;
        int j = 0;
        while (rowIndex <= 27) {
            Row row = sheet.getRow(rowIndex);
            for (int i = 1; i < row.getLastCellNum() - 1; i++, j++) {
                Cell cell = row.getCell(i);
                cell.setCellValue(data[j]);
            }
            rowIndex++;
        }
    }

    private void alertInfo(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void alertWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
