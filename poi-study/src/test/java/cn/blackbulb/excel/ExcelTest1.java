package cn.blackbulb.excel;

import cn.blackbulb.POIUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * @author wangcan
 */
public class ExcelTest1 {
    @Test
    public void excel() {
        String filePath = "/Users/bjqxdn0814/Desktop/test001.xlsx";
        String excelType = filePath.substring(filePath.lastIndexOf("."));
        String outfilePath = "/Users/bjqxdn0814/Desktop/test输出001.xlsx";

        try {
            InputStream is = new FileInputStream(filePath);
            OutputStream ou = new FileOutputStream(outfilePath);

            Workbook workbook = new XSSFWorkbook(is);
//            workbook.createSheet();
//            Sheet sheet = workbook.createSheet();
//            Row row = sheet.createRow(1);
//            Cell cell = row.createCell(0);
//            cell.setCellValue("123456");

            Sheet sheet = workbook.getSheetAt(0);

            //            sheet.removeRow(sheet.getRow(0));
//            sheet.removeRow(sheet.getRow(0));

            Row row = sheet.getRow(1);
            System.out.println(row.getRowNum());

            sheet.shiftRows(1,sheet.getLastRowNum(),-1);
            System.out.println(row.getRowNum());

            workbook.write(ou);
//            workbook.close();
            is.close();
            ou.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
