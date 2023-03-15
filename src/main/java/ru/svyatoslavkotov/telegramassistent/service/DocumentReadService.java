package ru.svyatoslavkotov.telegramassistent.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import ru.svyatoslavkotov.telegramassistent.model.Example;
import ru.svyatoslavkotov.telegramassistent.model.Lesson;
import ru.svyatoslavkotov.telegramassistent.model.WeakDay;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentReadService {

    private final String DOCUMENT_PATH ="";
    private final String DOCUMENT_NAME = "School_Scheduled.docx";

    public List<Lesson> read() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(DOCUMENT_PATH+DOCUMENT_NAME);
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fis));
            XWPFTable table = docxFile.getTables().get(0);
            List<XWPFTableRow> tableRows = table.getRows();
            List<Example> examples = new ArrayList<>();


            for (int count = 1; count < tableRows.size(); count++) {
                LocalDate date =  LocalDate.now();

                if (tableRows.get(count).getCell(1).getText().equals(WeakDay.MONDAY.getValue())) {
                    LocalDate day = saveDate(1);
                    count++;
                    while (tableRows.get(count).getTableCells().size()>2) {
                        lessons = parceRow(tableRows.get(count), lessons, day, date);
                        if (day.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()){
                            examples = parseExample(tableRows.get(count),examples,day, date);
                        }
                        count++;
                    }
                }
                if (tableRows.get(count).getCell(1).getText().equals(WeakDay.TUESDAY.getValue())) {
                    LocalDate day = saveDate(2);
                    count++;
                    while (tableRows.get(count).getTableCells().size()>2) {
                        lessons = parceRow(tableRows.get(count), lessons, day, date);
                        if (day.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()){
                            examples = parseExample(tableRows.get(count),examples,day, date);
                        }
                        count++;
                    }
                }
                if (tableRows.get(count).getCell(1).getText().equals(WeakDay.WEDNESDAY.getValue())) {
                    LocalDate day = saveDate(3);
                    count++;
                    while (tableRows.get(count).getTableCells().size()>2) {
                        lessons = parceRow(tableRows.get(count), lessons, day, date);
                        if (day.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()){
                            examples = parseExample(tableRows.get(count),examples,day, date);
                        }
                        count++;
                    }

                }
                if (tableRows.get(count).getCell(1).getText().equals(WeakDay.THURSDAY.getValue())) {
                    LocalDate day = saveDate(4);
                    count++;
                    while (tableRows.get(count).getTableCells().size()>2) {
                        lessons = parceRow(tableRows.get(count), lessons, day, date);
                        if (day.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()){
                            examples = parseExample(tableRows.get(count),examples,day, date);
                        }
                        count++;
                    }
                }
                if (tableRows.get(count).getCell(1).getText().equals(WeakDay.FRIDAY.getValue())) {
                    LocalDate day = saveDate(5);
                    count++;
                    while (tableRows.get(count).getTableCells().size()>2) {
                        lessons = parceRow(tableRows.get(count), lessons, day, date);
                        if (day.getDayOfWeek().getValue() == date.getDayOfWeek().getValue()){
                            examples = parseExample(tableRows.get(count),examples,day, date);
                        }
                        if (count + 1  != tableRows.size()){
                            count++;
                        }else {
                            break;
                        }
                    }
                }
            }

            lessons = writeExample(lessons,examples);

            return lessons;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    private List<Lesson> parceRow(XWPFTableRow row, List<Lesson> lessons, LocalDate day, LocalDate date){
        Lesson lesson = Lesson.builder()
                .title(row.getCell(1).getText())
                .number(Integer.parseInt(row.getCell(0).getText()))
                .dayOfWeak(day.getDayOfWeek().getValue())
                .date(day)
                .lastUpdate(date)
                .build();
        lessons.add(lesson);
        return  lessons;
    }

    private List<Example> parseExample ( XWPFTableRow row, List<Example> examples, LocalDate day, LocalDate date) {
            Example ex = new Example(row.getCell(1).getText());
            if (!row.getCell(2).getText().isEmpty()){
                ex.setExample(row.getCell(2).getText());
                ex.setActive(true);
            }
            if (row.getCell(3).getText().isEmpty()){
                ex.setExample(row.getCell(3).getText());
                ex.setActive(true);
            }
            if (ex.isActive()){
                examples.add(ex);
            }
            return examples;
    }

    private LocalDate saveDate(int dayOfMountSave){
        LocalDate currentDate = LocalDate.now();
        if (dayOfMountSave == currentDate.getDayOfWeek().getValue()) {
                return currentDate.plusDays(7);
            }else if (dayOfMountSave < currentDate.getDayOfWeek().getValue()) {
                return currentDate.plusDays((7+dayOfMountSave)-currentDate.getDayOfWeek().getValue());
            }else if (dayOfMountSave > currentDate.getDayOfWeek().getValue()) {
                return currentDate.plusDays(dayOfMountSave - currentDate.getDayOfWeek().getValue());
            }else {
                return currentDate;
            }
    }
    private List<Lesson> writeExample(List<Lesson> lessons, List<Example> examples) {
        for (Example e : examples) {
            int saveDate = LocalDate.now().getDayOfWeek().getValue() + 1;
            while (e.isActive()){
                if(saveDate > 5) {
                    saveDate = 1;
                }
                int finalSaveDate = saveDate;
                Lesson lesson = lessons.stream()
                        .filter(lesson1 -> lesson1.getDayOfWeak() == finalSaveDate)
                        .filter(lesson1 -> lesson1.getTitle().equals(e.getTitle())).findFirst().orElse(null);
                if (lesson != null){
                    lesson.setExampleRecommended(e.getExampleRecommended());
                    lesson.setExample(e.getExample());
                    e.setActive(false);
                }else {
                    saveDate ++;
                }
            }
        }
        return lessons;
    }
}
