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

public class ExcelController {
    String data[] = null;

    public void importDataFromExcel() throws IOException, SQLException {
        readFile();
    }
    public void exportDataInExcel() throws IOException {
        saveFile();
    }

    private void saveFile() throws IOException {
//        Stage stage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.setInitialFileName("Report");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("*.xls", "*.xls"),
//                new FileChooser.ExtensionFilter("*.xlsx", "*.xlsx")
//        );
//        File selectedFile = fileChooser.showSaveDialog(stage);
//        if (selectedFile != null) {
        if (data != null) {
            FileInputStream fis = new FileInputStream("./Title.xls");
            //String extension = fileExtension(selectedFile);

//            if (extension.equals("xls")) {
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            addDataInExcel(sheet);

            fis.close();

            FileOutputStream outputStream = new FileOutputStream("./Title.xls");
            wb.write(outputStream);

            wb.close();
            outputStream.close();
            alertInfo("File successfully saved!");
        } else {
            alertWarning("There is nothing to save!\nPlease, open a file with data.");
        }
//            } else {
//                XSSFWorkbook wb = new XSSFWorkbook(fis);
//                XSSFSheet sheet = wb.getSheetAt(0);
//                addDataInExcel(sheet);
//
//                fis.close();
//
//                FileOutputStream outputStream = new FileOutputStream("./Title.xls");
//                wb.write(outputStream);
//
//                wb.close();
//                outputStream.close();
//            }
            //copyWorkBooks(fis, wb,selectedFile);
       // }
    }

//    private void copyWorkBooks(FileInputStream fis, Workbook inputWB, File selectedFile) throws IOException {
//        int inputSheetCount = inputWB.getNumberOfSheets();
//
//        FileOutputStream fos = new FileOutputStream(selectedFile);
//        Workbook outputWB = new HSSFWorkbook();
//
//        for(int i=0; i < inputSheetCount - 1; i++) // inputSheetCount - 1 because Title.xls has 2 sheets
//        {
//            Sheet inputSheet = inputWB.getSheetAt(i);
////            String inputSheetName = inputWB.getSheetName(i);
//            Sheet outputSheet = inputWB.createSheet("Лист1");
//
//            // Create and call method to copy the sheet and content in new workbook.
//            copySheet(inputSheet, outputSheet);
//        }
//
//        outputWB.write(fos);
//        fos.close();
//    }

//    public static void copySheet(Sheet inputSheet, Sheet outputSheet) {
//        int rowCount = inputSheet.getLastRowNum();
//
//        int currentRowIndex = 0;
//        if (rowCount > 0) {
//            Iterator<Row> rowIterator = inputSheet.iterator();
//            while (rowIterator.hasNext()) {
//                int currentCellIndex = 0;
//                Row currentRow = rowIterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();
//                while (cellIterator.hasNext()) {
//                    // Creating new Row, Cell and Input value in the newly created sheet.
//                    String cellData = cellIterator.next().toString();
//                    if (currentCellIndex == 0)
//                        outputSheet.createRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
//                    else
//                        outputSheet.getRow(currentRowIndex).createCell(currentCellIndex).setCellValue(cellData);
//
//                    currentCellIndex++;
//                }
//                currentRowIndex++;
//            }
//        }
//    }

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
