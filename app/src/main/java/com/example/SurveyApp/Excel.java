package com.example.SurveyApp;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {
    String sitename;
    String siteid;
    String sitesharingstatus;
    String siteclassification;
    //String []itemname;
    //String []vendor;
    //String []quantity;
   // String []model;
   // String []remark;
   // String []equipmentcondition;
    Workbook workbook;
    CreationHelper creationHelper;
    Sheet sheet;
    int row=0;
    String filename;
    String foldername;
    //copy the arrays from the form.class to here and the excel file will be created

    public Excel(String sitename,String siteid,String sitesharingstatus,String siteclassification){
       int row=0;
        this.sitename=sitename;
        this.siteid=siteid;
        this.siteclassification=siteclassification;
        this.sitesharingstatus=sitesharingstatus;

        workbook=new XSSFWorkbook();
         creationHelper=workbook.getCreationHelper();
        //sheet is create with sitename;
        sheet=workbook.createSheet(sitename);
        Row headerrow=sheet.createRow(0);
        headerrow.createCell(0).setCellValue("Item name");
         headerrow.createCell(1).setCellValue("Quantity");
          headerrow.createCell(2).setCellValue("Vendor");
           headerrow.createCell(3).setCellValue("Model");
           headerrow.createCell(3).setCellValue("Equipment Condition");
           headerrow.createCell(3).setCellValue("Remark");
           ++row;
            filename=sitename+".xlsx";
          foldername=sitename;



    }

   

   /* void additem_sub_components(String []itemname,String[]quantity,String []vendor,String []model,String []equipmentcondition,String []remark){
        // adding the components to the spreadsheet



        for(int i=row;i<itemname.length;i++){

            Row sheetrow=sheet.createRow(row);
            sheetrow.createCell(0).setCellValue(itemname[i-1]);
            sheetrow.createCell(1).setCellValue(quantity[i-1]);
            sheetrow.createCell(3).setCellValue(vendor[i-1]);
            sheetrow.createCell(4).setCellValue(model[i-1]);
            sheetrow.createCell(5).setCellValue(equipmentcondition[i-1]);
            sheetrow.createCell(6).setCellValue(remark[i-1]);


        }
    }*/


        

        void additem_sub_components_noarray(String itemname,String quantity,String vendor,String model,String equipmentcondition,String remark){

             Row sheetrow=sheet.createRow(row);
               sheetrow.createCell(0).setCellValue(itemname);
            sheetrow.createCell(1).setCellValue(quantity);
            sheetrow.createCell(3).setCellValue(vendor);
            sheetrow.createCell(4).setCellValue(model);
            sheetrow.createCell(5).setCellValue(equipmentcondition);
            sheetrow.createCell(6).setCellValue(remark);
              ++row;

          


        }

        //call this method after all items have been added
        void finishexcel(String folder_location){

     
            String fileName = "FileName.xlsx";


String extStorageDirectory = folder_location;//kon folder a save  hobe tar directory r string aikhane boshbe

File file = new File(folder_location, filename);
try {
    file.createNewFile();
  } catch (IOException e1) {
     e1.printStackTrace();
  }

    try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






        }
      







    }

