package cn.blackbulb.excel;

import cn.blackbulb.POIUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcan
 */
public class ExcelTest {

    @Test
    public void readerExcel() {

        Map<String, Object> student1 = new HashMap<>();
        student1.put("name", "老外");
        student1.put("age", "18");
        student1.put("address", "故宫");
        Map<String, Object> student2 = new HashMap<>();
        student2.put("name", "老外1");
        student2.put("age", "181");
        student2.put("address", "故宫1");
        ArrayList<Map<String, Object>> students = Lists.newArrayList();
        students.add(student1);
        students.add(student2);
        Map<String, Object> commonParam = new HashMap<>();

        Map<String, List<Map<String, Object>>> arrayMap = new HashMap<>();
        arrayMap.put("students", students);
        commonParam.put("title", "学生表零零落落");
        BindParam param = new BindParam();
        param.setCommonParam(commonParam);
        param.setArrayParam(arrayMap);
        String filePath = "/Users/bjqxdn0814/Desktop/template2.xlsx";
        String out = "/Users/bjqxdn0814/Desktop/输出2.xlsx";
        String excelType = filePath.substring(filePath.lastIndexOf("."));

        try {
            InputStream is = new FileInputStream(filePath);
            OutputStream os = new FileOutputStream(out);
            Workbook workbook = null;
            if (".xls".equals(excelType)) {
                workbook = new HSSFWorkbook(is);
            } else if (".xlsx".equals(excelType)) {
                workbook = new XSSFWorkbook(is);
            }
            if (workbook != null) {
                Sheet sheet = workbook.getSheetAt(0);
                int rownum = sheet.getLastRowNum();
                for (int n = 0; n <= rownum; n++) {
                    n = convertRow(sheet, n, param);
                    rownum = sheet.getLastRowNum();
                }
            }
            workbook.write(os);
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int convertRow(Sheet sheet, int rowNum, BindParam param) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return rowNum;
        }
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int proceedRowNum = convertCell(cell, param);
            if (proceedRowNum > rowNum) {
                return proceedRowNum;
            }
        }

        return rowNum;

    }

    public static int convertCell(Cell cell, BindParam param) {
        CellExpressionType cellExpressionType = CellExpressionType.getExpressionTypeByCell(cell);
        if (cellExpressionType == null) {
            return cell.getRowIndex();
        }
        switch (cellExpressionType) {
            case PARAM:
                return convertParamCell(cell, param);
            //                break;
            case FOR_START:
                return convertForStartCell(cell, param);
            //                break;
            case FOR_END:
                break;
        }
        return cell.getRowIndex();

    }

    public static int convertParamCell(Cell cell, BindParam param) {
        String paramExpression = cell.getStringCellValue();
        Pattern p = Pattern.compile(CellExpressionType.PARAM.getExpression());
        Matcher matcher = p.matcher(paramExpression);
        if (matcher.find()) {
            String key = matcher.group(1);
            String value = param.getCommonParam().get(key) == null ? "" : String.valueOf(param.getCommonParam().get(key));
            cell.setCellValue(value);
        }
        return cell.getRowIndex();

    }

    public static int convertForStartCell(Cell cell, BindParam param) {
        Sheet sheet = cell.getSheet();
        Pattern pattern = Pattern.compile(CellExpressionType.FOR_START.getExpression());
        Matcher matcher = pattern.matcher(cell.getStringCellValue());
        matcher.find();
        String itemKey = matcher.group(1);
        String arrayKey = matcher.group(2);
        Row forStartRow = cell.getRow();
        Row forEndRow = getEndRowCorrespondForStarRow(forStartRow);
        //没有for循环的结束语句
        if (forEndRow == null) {
            POIUtils.removeExcelRow(sheet, forStartRow.getRowNum());
        }
        //如果for循环开始语句与结束语句相邻，删除for循环的开始与结束语句
        if (forStartRow.getRowNum() + 1 == forEndRow.getRowNum()) {
            POIUtils.removeExcelRow(sheet, forStartRow.getRowNum());
            POIUtils.removeExcelRow(sheet, forEndRow.getRowNum());
        }

        Map<String, List<Map<String, Object>>> arrayParamMap = param.getArrayParam();
        List<Map<String, Object>> arrayParam = arrayParamMap.get(arrayKey);

        if (CollectionUtils.isNotEmpty(arrayParam)) {
            for (Map<String, Object> map : arrayParam) {
                //把array中的参数取出放到commonParam中，让循环参数变为普通参数。
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    param.getCommonParam().put(itemKey + "." + entry.getKey(), entry.getValue());
                }
                Row currRow = sheet.getRow(forStartRow.getRowNum() + 1);
                int currentRowNum = currRow.getRowNum();
                while (!isForEndRow(currRow)) {
                    sheet.shiftRows(forStartRow.getRowNum(), sheet.getLastRowNum(), 1);
                    if(currRow!=null){
                        Row copyRow = sheet.createRow(forStartRow.getRowNum() - 1);
                        POIUtils.copyRow(currRow, copyRow, true);
                        convertRow(sheet, copyRow.getRowNum(), param);
                    }
                    currentRowNum+=2;
                    currRow = sheet.getRow(currentRowNum );

                }

            }

        }
        int proceedRowNum = forStartRow.getRowNum() - 1;
        int currRemoveRowNum = forEndRow.getRowNum();
        int forStartRowNum = forStartRow.getRowNum();
        while (currRemoveRowNum>=forStartRowNum){
            POIUtils.removeExcelRow(sheet,currRemoveRowNum);
            currRemoveRowNum--;
        }
        return proceedRowNum;

    }

    /**
     * 判断该行是否为 for循环的开始行，for循环行的第一个cell 必须是 {for item in array}的格式
     */
    public static boolean isForStartRow(Row row) {
        return row != null && row.getCell(0) != null && CellExpressionType.FOR_START == CellExpressionType.getExpressionTypeByCell(row.getCell(0));
    }

    /**
     * 判断该行是否为 for循环的开始行，for循环行的第一个cell 必须是 {for end}
     */
    public static boolean isForEndRow(Row row) {
        return row != null && row.getCell(0) != null && CellExpressionType.FOR_END == CellExpressionType.getExpressionTypeByCell(row.getCell(0));
    }

    /**
     * 获取某个for循环开始行对应的结束行
     */
    public static Row getEndRowCorrespondForStarRow(Row forStarRow) {
        Sheet sheet = forStarRow.getSheet();
        int lastRowNum = sheet.getLastRowNum();
        int forCout = 0;
        for (int i = forStarRow.getRowNum(); i < lastRowNum + 1; i++) {
            Row row = sheet.getRow(i);
            if (isForStartRow(row)) {
                forCout++;
            }
            if (isForEndRow(row)) {
                forCout--;
                if (forCout == 0) {
                    return row;
                }
            }

        }
        return null;
    }

    /**
     * 获取某个forEnd行对应的forStart行
     */
    public static Row getForStartRoWCorrespondEndRow(Row forEndRow) {
        Sheet sheet = forEndRow.getSheet();
        int forCout = 0;
        for (int i = forEndRow.getRowNum(); i > 0; i--) {
            Row row = sheet.getRow(i);
            if (isForEndRow(row)) {
                forCout++;
            }
            if (isForStartRow(row)) {
                forCout--;
                if (forCout == 0) {
                    return row;
                }
            }

        }
        return null;
    }

}
