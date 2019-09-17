package com.example.SurveyApp.util;

import android.os.Environment;

import com.example.SurveyApp.model.Model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Excel {

    private Workbook workbook;
    private Sheet sheet;
    private String filename;

    public Excel(String siteName){
        workbook=new XSSFWorkbook();
        sheet=workbook.createSheet(siteName);
        filename=siteName+".xlsx";
        Row headerRow=sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Item name");
        headerRow.createCell(1).setCellValue("Quantity");
        headerRow.createCell(2).setCellValue("Vendor");
        headerRow.createCell(3).setCellValue("Model");
        headerRow.createCell(3).setCellValue("Equipment Condition");
        headerRow.createCell(3).setCellValue("Remark");
    }

    public void addAll(ArrayList<Model> list){
        for(int i=1;i<list.size();i++){
            Model model = list.get(i-1);
            Row sheetrow=sheet.createRow(i);
            sheetrow.createCell(0).setCellValue(model.getName());
            sheetrow.createCell(1).setCellValue(model.getQuantity());
            sheetrow.createCell(3).setCellValue(model.getVendor());
            sheetrow.createCell(4).setCellValue(model.getModel());
            sheetrow.createCell(5).setCellValue(model.getCondition());
            sheetrow.createCell(6).setCellValue(model.getRemark());
        }
    }

    public void add(Model model, int position){
        Row sheetrow=sheet.createRow(position);
        sheetrow.createCell(0).setCellValue(model.getName());
        sheetrow.createCell(1).setCellValue(model.getQuantity());
        sheetrow.createCell(3).setCellValue(model.getVendor());
        sheetrow.createCell(4).setCellValue(model.getModel());
        sheetrow.createCell(5).setCellValue(model.getCondition());
        sheetrow.createCell(6).setCellValue(model.getRemark());
    }

    public void finish(){
        File root = new File(Environment.getExternalStorageDirectory(), "Survey");
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root, filename);
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
