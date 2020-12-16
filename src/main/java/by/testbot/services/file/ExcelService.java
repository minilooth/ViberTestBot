package by.testbot.services.file;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.Answer;
import by.testbot.models.Dialogue;
import by.testbot.models.enums.AnswerType;
import by.testbot.services.AnswerService;
import by.testbot.services.DialogueService;
import lombok.SneakyThrows;

@Service
public class ExcelService {
    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private AnswerService answerService;

    public String generateExcelAndWriteActualData() {
        return generateExcel(dialogueService.getAll());
    }
    
    @SneakyThrows
    private String generateExcel(List<Dialogue> dialogues) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(10);

        Map<Long, List<Dialogue>> dialoguesGrouped = new TreeMap<>(dialogues.stream().collect(Collectors.groupingBy(d -> d.getBotMessagesLastUpdate()))); 

        Integer rowCount = 0;

        for (Long botMessageUpdate : dialoguesGrouped.keySet()) {
            List<Dialogue> dialoguesList = dialoguesGrouped.get(botMessageUpdate);
            Dialogue tmpDialogue = dialoguesList.stream().findAny().orElse(null);
            Integer answersCount = 0;

            if (tmpDialogue != null) {
                answersCount = tmpDialogue.getMustBeAnswers();
            }

            createBotMessagesUpdateRow(sheet, rowCount++, botMessageUpdate);
            createHeaderRow(sheet, answersCount, rowCount++);

            for (Dialogue dialogue : dialoguesList) {
                Row row = sheet.createRow(rowCount++);
                writeDialogue(dialogue, row, sheet, answersCount);
            }

            rowCount++;
        }

        String filename = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").format(new Date()) + ".xlsx";

        try(FileOutputStream fileOutputStream = new FileOutputStream(FileService.EXCEL_FOLDER_PATH + filename)) {
            workbook.write(fileOutputStream);
        }

        workbook.close();

        return filename;
    }

    private void writeDialogue(Dialogue dialogue, Row row, Sheet sheet, Integer answersCount) {
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

        cell = row.createCell(5);
        cell.setCellValue(dialogue.getClient().getPhoneNumber() != null ? dialogue.getClient().getPhoneNumber() : "Еще не указал");

        cell = row.createCell(6);
        cell.setCellValue(dialogue.getDialogueIsOver() ? "Да" : "Нет");

        Integer columnCounter = 7;

        List<Answer> answers = answerService.getAllByDialogue(dialogue);

        for (Integer i = 0; i < answers.size() && i < answersCount; i++) {
            cell = row.createCell(columnCounter);
            cell.setCellValue(answers.get(i).getAnswer());
            cell.setCellStyle(answers.get(i).getAnswerType() == AnswerType.POSITIVE ? positiveCellStyle : negativeCellStyle);
            columnCounter++;
        }

        for (Integer i = 0; i < answersCount - answers.size(); i++) {
            cell = row.createCell(columnCounter);
            cell.setCellValue("Еще не указал");
            cell.setCellStyle(negativeCellStyle);
            columnCounter++;
        }
    }

    private void createBotMessagesUpdateRow(Sheet sheet, Integer rowCount, Long botMessageUpdate) {
        Row row = sheet.createRow(rowCount);

        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 6));

        Cell cell = row.createCell(0);
        cell.setCellValue("Обновление сообщений бота от: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(botMessageUpdate)));
    }

    private void createHeaderRow(Sheet sheet, Integer answersCount, Integer rowCount) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);

        Row row = sheet.createRow(rowCount);

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
        cell.setCellValue("Мобильный телефон");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("Диалог окончен");
        cell.setCellStyle(cellStyle);

        Integer columnCounter = 7;

        for (Integer i = 0; i < answersCount; i++) {
            cell = row.createCell(columnCounter);
            cell.setCellValue("Шаг " + (i + 1));
            cell.setCellStyle(cellStyle);
            columnCounter++;
        }
    }
}
