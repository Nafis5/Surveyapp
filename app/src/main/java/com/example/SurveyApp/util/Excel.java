package com.example.SurveyApp.util;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {
    private Workbook workbook;
    private Sheet sheet;
    private String filename;
    private int row=0;

    public Excel(String sitename,String siteid,String sitesharingstatus,String siteclassification){
        workbook=new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        sheet=workbook.createSheet(sitename);
        filename=sitename+".xlsx";
        Row headerRow=sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Item name");
        headerRow.createCell(1).setCellValue("Quantity");
        headerRow.createCell(2).setCellValue("Vendor");
        headerRow.createCell(3).setCellValue("Model");
        headerRow.createCell(3).setCellValue("Equipment Condition");
        headerRow.createCell(3).setCellValue("Remark");
        row++;
    }

    void addAll(String[] itemname, String[] quantity, String[] vendor, String[] model, String[] equipmentCondition, String[] remark){
        for(int i=row;i<itemname.length;i++){
            Row sheetrow=sheet.createRow(row);
            sheetrow.createCell(0).setCellValue(itemname[i-1]);
            sheetrow.createCell(1).setCellValue(quantity[i-1]);
            sheetrow.createCell(3).setCellValue(vendor[i-1]);
            sheetrow.createCell(4).setCellValue(model[i-1]);
            sheetrow.createCell(5).setCellValue(equipmentCondition[i-1]);
            sheetrow.createCell(6).setCellValue(remark[i-1]);
        }
    }

    void add(String itemname, String quantity, String vendor, String model, String equipmentCondition, String remark){
        Row sheetrow=sheet.createRow(row);
        sheetrow.createCell(0).setCellValue(itemname);
        sheetrow.createCell(1).setCellValue(quantity);
        sheetrow.createCell(3).setCellValue(vendor);
        sheetrow.createCell(4).setCellValue(model);
        sheetrow.createCell(5).setCellValue(equipmentCondition);
        sheetrow.createCell(6).setCellValue(remark);
        row++;
    }

    void finish(String folderLocation){
        File file = new File(folderLocation, filename);
        try {
            file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
