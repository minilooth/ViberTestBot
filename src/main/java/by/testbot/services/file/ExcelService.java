package by.testbot.services.file;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.Dialogue;
import by.testbot.services.DialogueService;
import lombok.SneakyThrows;

@Service
public class ExcelService {
    @Autowired
    private DialogueService dialogueService;

    public String generateExcelAndWriteActualData() {
        return generateExcel(dialogueService.getAll());
    }
    
    @SneakyThrows
    private String generateExcel(List<Dialogue> dialogues) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(10);

        createHeaderRow(sheet);

        Integer rowCount = 1;

        for(Dialogue dialogue : dialogues) {
            Row row = sheet.createRow(rowCount++);
            writeDialogue(dialogue, row, sheet);
        }

        String filename = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").format(new Date()) + ".xlsx";

        try(FileOutputStream fileOutputStream = new FileOutputStream(FileService.EXCEL_FOLDER_PATH + filename)) {
            workbook.write(fileOutputStream);
        }

        workbook.close();

        return filename;
    }

    private void writeDialogue(Dialogue dialogue, Row row, Sheet sheet) {
        CellStyle positiveCellStyle = sheet.getWorkbook().createCellStyle();
        positiveCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        positiveCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());

        CellStyle negativeCellStyle = sheet.getWorkbook().createCellStyle();
        negativeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        negativeCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());

        Cell cell = row.createCell(0);
        cell.setCellValue(dialogue.getClient().getName() != null ? dialogue.getClient().getName() : "Еще не указал");

        cell = row.createCell(1);
        cell.setCellValue(dialogue.getBrand() != null ? dialogue.getBrand() : "Еще не указал");

        cell = row.createCell(2);
        cell.setCellValue(dialogue.getModel() != null ? dialogue.getModel() : "Еще не указал");

        cell = row.createCell(3);
        cell.setCellValue(dialogue.getYearFrom() != null ? dialogue.getYearFrom().toString() : "Еще не указал");

        cell = row.createCell(4);
        cell.setCellValue(dialogue.getYearTo() != null ? dialogue.getYearTo().toString() : "Еще не указал");

        // cell = row.createCell(5);
        // cell.setCellValue(dialogue.getStep1Answer() != null ? dialogue.getStep1Answer() : "");
        // if (dialogue.getStep1Answer() != null && dialogue.getStep1Answer().equals("Да")) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        // cell = row.createCell(6);
        // cell.setCellValue(dialogue.getStep2Answer() != null ? dialogue.getStep2Answer() : "");
        // if (dialogue.getStep2Answer() != null && dialogue.getStep2Answer().equals("Да")) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        // cell = row.createCell(7);
        // cell.setCellValue(dialogue.getStep3Answer() != null ? dialogue.getStep3Answer() : "");
        // if (dialogue.getStep3Answer() != null && dialogue.getStep3Answer().equals("Да")) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        // cell = row.createCell(8);
        // cell.setCellValue(dialogue.getStep4Answer() != null ? dialogue.getStep4Answer() : "");
        // if (dialogue.getStep4Answer() != null) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        // cell = row.createCell(9);
        // cell.setCellValue(dialogue.getStep5Answer() != null ? dialogue.getStep5Answer() : "");
        // if (dialogue.getStep5Answer() != null && dialogue.getStep5Answer().equals("Да")) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        // cell = row.createCell(10);
        // cell.setCellValue(dialogue.getStep6Answer() != null ? dialogue.getStep6Answer() : "");
        // if (dialogue.getStep6Answer() != null && dialogue.getStep6Answer().equals("Да")) {
        //     cell.setCellStyle(positiveCellStyle);
        // }
        // else {
        //     cell.setCellStyle(negativeCellStyle);
        // }

        cell = row.createCell(11);
        cell.setCellValue(dialogue.getClient().getPhoneNumber() != null ? dialogue.getClient().getPhoneNumber() : "Еще не указал");

        cell = row.createCell(12);
        cell.setCellValue(dialogue.getDialogIsOver() ? "Да" : "Нет");
    }

    private void createHeaderRow(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Имя");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Марка");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Модель");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Год выпуска от");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Год выпуска до");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        cell.setCellValue("Шаг 1");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("Шаг 2");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(7);
        cell.setCellValue("Шаг 3");
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell(8);
        cell.setCellValue("Шаг 4");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(9);
        cell.setCellValue("Шаг 5");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(10);
        cell.setCellValue("Шаг 6");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(11);
        cell.setCellValue("Мобильный телефон");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(12);
        cell.setCellValue("Диалог окончен");
        cell.setCellStyle(cellStyle);
    }
}
