//package cn.blackbulb.excel;
//
//import com.google.common.collect.Sets;
//import com.jingdata.common.exception.BusinessException;
//import com.jingdata.paas.org.common.exception.ExceptionMessage;
//import com.jingdata.template.office.OfficeFormateUtil;
//import com.jingdata.template.office.POIUtils;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
///**
// * @author wangcan
// */
//public class ExcelTempalgeEngine {
//
//    public static Workbook getWorkbook(InputStream is, String excelType) throws IOException {
//        Workbook workbook = null;
//        if (".xls".equals(excelType)) {
//            workbook = new HSSFWorkbook(is);
//        } else if (".xlsx".equals(excelType)) {
//            workbook = new XSSFWorkbook(is);
//        }
//        return workbook;
//    }
//
//    /**
//     * 校验Excel模板,目前只校验for 循环语句的
//     */
//    public static void checkExcel(InputStream is, String excelType) throws IOException {
//        Workbook workbook = getWorkbook(is, excelType);
//        if (workbook != null) {
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//                Sheet sheet = workbook.getSheetAt(i);
//                checkSheet(sheet);
//            }
//        }
//    }
//
//    /**
//     * 校验sheet，目前只校验for语句
//     */
//    public static void checkSheet(Sheet sheet) {
//        if (sheet == null) {
//            return;
//        }
//        int forStartCount = 0;
//        int endForCount = 0;
//        for (int i = 0; i < sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//            if (isForStartRow(row)) {
//                forStartCount++;
//            }
//            if (isForEndRow(row)) {
//                endForCount++;
//            }
//            if (forStartCount < endForCount) {
//                throw new BusinessException(ExceptionMessage.COMMON_ERROR_MSG.getCode(),
//                        String.format("[%s]的第[%d]行for循环的结束语句在开始语句之前", sheet.getSheetName(), i));
//            }
//
//        }
//        if (forStartCount != endForCount) {
//            throw new BusinessException(ExceptionMessage.COMMON_ERROR_MSG.getCode(), String.format("[%s]的for循环的结束语句在开始语句之前", sheet.getSheetName()));
//        }
//
//    }
//
//    /**
//     * 转化模版
//     */
//    public static void convertExcel(Workbook workbook, BindParam bindParams) throws IOException {
//
//        //        String excelType = filePath.substring(filePath.lastIndexOf("."));
//
//        if (workbook != null) {
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//                Sheet sheet = workbook.getSheetAt(i);
//                int rownum = sheet.getLastRowNum();
//                for (int n = 0; n <= rownum; n++) {
//                    n = convertRow(sheet, n, bindParams);
//                    rownum = sheet.getLastRowNum();
//                }
//            }
//
//        }
//
//    }
//
//    /**
//     * 转换某一行测参数
//     */
//    public static int convertRow(Sheet sheet, int rowNum, BindParam param) {
//        Row row = sheet.getRow(rowNum);
//        if (row == null) {
//            return rowNum;
//        }
//        Iterator<Cell> cellIterator = row.cellIterator();
//
//        while (cellIterator.hasNext()) {
//            Cell cell = cellIterator.next();
//            int proceedRowNum = convertCell(cell, param);
//            if (proceedRowNum != rowNum) {
//                return proceedRowNum;
//            } else if (row != sheet.getRow(rowNum)) {
//                return proceedRowNum;
//            }
//
//        }
//
//        return rowNum;
//
//    }
//
//    /**
//     * 转换某一格参数
//     */
//    public static int convertCell(Cell cell, BindParam param) {
//        CellExpressionType cellExpressionType = CellExpressionType.getExpressionTypeByCell(cell);
//        if (cellExpressionType == null) {
//            return cell.getRowIndex();
//        }
//        switch (cellExpressionType) {
//            case PARAM:
//                return convertParamCell(cell, param);
//            //                break;
//            case FOR_START:
//                return convertForStartCell(cell, param);
//            case FORMAT_PARAM:
//                return convertFormatParamCell(cell, param);
//            //                break;
//            case FOR_END:
//                break;
//            case FUNCTION:
//                return convertFormatParamCell(cell, param);
//
//        }
//        return cell.getRowIndex();
//
//    }
//
//    public static int convertFunctionCell(Cell cell, BindParam bindParam) {
//        String functionExpression = cell.getStringCellValue();
//        String expression = CellExpressionType.FUNCTION.getExpression();
//        Pattern pattern = Pattern.compile(expression);
//        Matcher matcher = pattern.matcher(functionExpression);
//        matcher.find();
//        String key = matcher.group(2);
//        String paramKey = matcher.group(5);
//        Pattern numPattern = Pattern.compile("^[-\\+]?[\\d]*$");
//        if (bindParam.getArrayParam() != null && CollectionUtils.isNotEmpty(bindParam.getArrayParam().get(key))) {
//            switch (matcher.group(1)) {
//                case "count":
//                    cell.setCellValue(bindParam.getArrayParam().size());
//                    break;
//                case "sum":
//                    double sum = bindParam.getArrayParam().get(key).stream().filter(m -> {
//                        Object v = m.get(paramKey);
//                        return v != null && numPattern.matcher(v.toString()).find();
//                    }).collect(Collectors.summingDouble(x -> (Double) x.get(paramKey)));
//                    cell.setCellValue(sum);
//                    break;
//                case "average":
//                    double avage = bindParam.getArrayParam().get(key).stream().filter(m -> {
//                        Object v = m.get(paramKey);
//                        return v != null && numPattern.matcher(v.toString()).find();
//                    }).collect(Collectors.averagingDouble(x -> (Double) x.get(paramKey)));
//                    cell.setCellValue(avage);
//                    break;
//                case "min":
//                    Optional<Double> min = bindParam.getArrayParam().get(key).stream().filter(m -> {
//                        Object v = m.get(paramKey);
//                        return v != null && numPattern.matcher(v.toString()).find();
//                    }).map(objectMap -> {
//                        return Double.valueOf(objectMap.get(paramKey).toString());
//                    }).min(Double::compareTo);
//                    cell.setCellValue(String.valueOf(min.get()));
//                    break;
//                case "max":
//                    Optional<Double> max = bindParam.getArrayParam().get(key).stream().filter(m -> {
//                        Object v = m.get(paramKey);
//                        return v != null && numPattern.matcher(v.toString()).find();
//                    }).map(objectMap -> {
//                        return Double.valueOf(objectMap.get(paramKey).toString());
//                    }).max(Double::compareTo);
//                    cell.setCellValue(String.valueOf(max.get()));
//                    break;
//
//            }
//        }
//        return cell.getRowIndex();
//
//    }
//
//    /**
//     * 转换{key}类型
//     */
//    public static int convertParamCell(Cell cell, BindParam param) {
//        String paramExpression = cell.getStringCellValue();
//        Pattern p = Pattern.compile(CellExpressionType.PARAM.getExpression());
//        Matcher matcher = p.matcher(paramExpression);
//        String cellValue = paramExpression;
//        while (matcher.find()) {
//            String key = matcher.group(1);
//            String allKey = matcher.group(0);
//            if (param.getCommonParam().get(key) != null) {
//                Object value = param.getCommonParam().get(key);
//                /*if (value instanceof Double || value instanceof Float || value instanceof Integer) {
//                    cell.setCellType(CellType.NUMERIC);
//                    cell.setCellValue(Double.valueOf(value.toString()));
//                } else {
//
//                }*/
//
//                cellValue = cellValue.replace(allKey, String.valueOf(value));
//
//            }else{
//                cellValue = cellValue.replace(allKey, "");
//            }
//
//        }
//        cell.setCellValue(cellValue);
//        return cell.getRowIndex();
//
//    }
//
//    /**
//     * 转换需要格式化的单元格
//     */
//    public static int convertFormatParamCell(Cell cell, BindParam param) {
//        String paramExpression = cell.getStringCellValue();
//        Pattern p = Pattern.compile(CellExpressionType.FOR_END.getExpression());
//        Matcher matcher = p.matcher(paramExpression);
//        if (matcher.find()) {
//            String formatType = matcher.group(1);
//            String key = matcher.group(2);
//            Object value = param.getCommonParam().get(key);
//            if (value != null) {
//                Object o = OfficeFormateUtil.formateValue(formatType, value);
//                cell.setCellValue(String.valueOf(o));
//
//            }
//        }
//        return cell.getRowIndex();
//
//    }
//
//    /**
//     * 转换{for it in arraykey}行到{end for}行
//     */
//    public static int convertForStartCell(Cell cell, BindParam param) {
//        Sheet sheet = cell.getSheet();
//        Pattern pattern = Pattern.compile(CellExpressionType.FOR_START.getExpression());
//        Matcher matcher = pattern.matcher(cell.getStringCellValue());
//        matcher.find();
//        String itemKey = matcher.group(1);
//        String arrayKey = matcher.group(2);
//        Row forStartRow = cell.getRow();
//        int forStarindex = forStartRow.getRowNum();
//        Row forEndRow = getEndRowCorrespondForStarRow(forStartRow);
//        //没有for循环的结束语句
//        if (forEndRow == null) {
//            POIUtils.removeExcelRow(sheet, forStartRow.getRowNum());
//            return forStarindex - 1;
//        }
//        //如果for循环开始语句与结束语句相邻，删除for循环的开始与结束语句
//        if (forStartRow.getRowNum() + 1 == forEndRow.getRowNum()) {
//            POIUtils.removeExcelRow(sheet, forStartRow.getRowNum());
//            POIUtils.removeExcelRow(sheet, forEndRow.getRowNum());
//            return forStarindex;
//        }
//
//        Map<String, List<Map<String, Object>>> arrayParamMap = param.getArrayParam();
//        List<Map<String, Object>> arrayParam = arrayParamMap.get(arrayKey);
//
//        if (CollectionUtils.isNotEmpty(arrayParam)) {
//            for (Map<String, Object> map : arrayParam) {
//                //把array中的参数取出放到commonParam中，让循环参数变为普通参数。
//                Set<Map.Entry<String, Object>> entries = map.entrySet();
//                for (Map.Entry<String, Object> entry : entries) {
//                    param.getCommonParam().put(itemKey + "." + entry.getKey(), entry.getValue());
//                }
//                Row currRow = sheet.getRow(forStartRow.getRowNum() + 1);
//                int currentRowNum = currRow.getRowNum();
//                while (!isForEndRow(currRow)) {
//                    sheet.shiftRows(forStartRow.getRowNum(), sheet.getLastRowNum(), 1);
//                    if (currRow != null) {
//                        Row copyRow = sheet.createRow(forStartRow.getRowNum() - 1);
//                        POIUtils.copyRow(currRow, copyRow, true);
//                        convertRow(sheet, copyRow.getRowNum(), param);
//                    }
//                    currentRowNum += 2;
//                    currRow = sheet.getRow(currentRowNum);
//
//                }
//
//            }
//
//        }
//        int proceedRowNum = forStartRow.getRowNum() - 1;
//        int currRemoveRowNum = forEndRow.getRowNum();
//        int forStartRowNum = forStartRow.getRowNum();
//        while (currRemoveRowNum >= forStartRowNum) {
//            POIUtils.removeExcelRow(sheet, currRemoveRowNum);
//            currRemoveRowNum--;
//        }
//        return proceedRowNum;
//
//    }
//
//    /**
//     * 判断该行是否为 for循环的开始行，for循环行的第一个cell 必须是 {for item in array}的格式
//     */
//    public static boolean isForStartRow(Row row) {
//        return row != null && row.getCell(0) != null && CellExpressionType.FOR_START == CellExpressionType.getExpressionTypeByCell(row.getCell(0));
//    }
//
//    /**
//     * 判断该行是否为 for循环的开始行，for循环行的第一个cell 必须是 {for end}
//     */
//    public static boolean isForEndRow(Row row) {
//        return row != null && row.getCell(0) != null && CellExpressionType.FOR_END == CellExpressionType.getExpressionTypeByCell(row.getCell(0));
//    }
//
//    /**
//     * 获取某个for循环开始行对应的结束行
//     */
//    public static Row getEndRowCorrespondForStarRow(Row forStarRow) {
//        Sheet sheet = forStarRow.getSheet();
//        int lastRowNum = sheet.getLastRowNum();
//        int forCout = 0;
//        for (int i = forStarRow.getRowNum(); i < lastRowNum + 1; i++) {
//            Row row = sheet.getRow(i);
//            if (isForStartRow(row)) {
//                forCout++;
//            }
//            if (isForEndRow(row)) {
//                forCout--;
//                if (forCout == 0) {
//                    return row;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    /**
//     * 获取某个forEnd行对应的forStart行
//     */
//    public static Row getForStartRoWCorrespondEndRow(Row forEndRow) {
//        Sheet sheet = forEndRow.getSheet();
//        int forCout = 0;
//        for (int i = forEndRow.getRowNum(); i > 0; i--) {
//            Row row = sheet.getRow(i);
//            if (isForEndRow(row)) {
//                forCout++;
//            }
//            if (isForStartRow(row)) {
//                forCout--;
//                if (forCout == 0) {
//                    return row;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    public static Set<String> getAllExpressionKey(Workbook workbook) {
//        Set<String> keys = new HashSet<>();
//        if (workbook != null) {
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//                Set<String> allExpressionKeyOfSheet = getAllExpressionKeyOfSheet(workbook.getSheetAt(i));
//                if (CollectionUtils.isNotEmpty(allExpressionKeyOfSheet)) {
//                    keys.addAll(allExpressionKeyOfSheet);
//                }
//            }
//        }
//        return keys;
//    }
//
//    /**
//     * 获取一sheet需要获得的key
//     */
//    public static Set<String> getAllExpressionKeyOfSheet(Sheet sheet) {
//        if (sheet == null) {
//            return Collections.EMPTY_SET;
//        }
//        Set<String> cells = new HashSet<>();
//        for (int i = 0; i < sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//            Set<String> rowKeys = getAllExpressionKeyOfRow(row, new HashSet<>());
//            if (CollectionUtils.isNotEmpty(rowKeys)) {
//                cells.addAll(rowKeys);
//            }
//        }
//        return cells;
//    }
//
//    /**
//     * 获取某一行需要获得的key
//     */
//    public static Set<String> getAllExpressionKeyOfRow(Row row, Set<String> forItemKeys) {
//        if (row == null) {
//            return Collections.EMPTY_SET;
//        }
//        Set<String> cells = new HashSet<>();
//        Iterator<Cell> cellIterator = row.cellIterator();
//        while (cellIterator.hasNext()) {
//            Cell next = cellIterator.next();
//            String key = getExpressionKeyOfCell(next, forItemKeys);
//            if (StringUtils.isNotBlank(key)) {
//                cells.add(key);
//            }
//        }
//        return cells;
//    }
//
//    /**
//     * 获取某一个单元格需要获取的key
//     */
//    // TODO: 2020/4/14 一个cell中可能有多个key在此没有考虑虽然没有因为bug但以后要优化一下
//    public static String getExpressionKeyOfCell(Cell cell, Set<String> forItemKeys) {
//        if (cell == null) {
//            return null;
//        }
//        CellExpressionType expressionType = CellExpressionType.getExpressionTypeByCell(cell);
//        if (expressionType == null) {
//            return null;
//        }
//        switch (expressionType) {
//            case FOR_END:
//                return null;
//            case FOR_START:
//                Pattern pattern = Pattern.compile(CellExpressionType.FOR_START.getExpression());
//                Matcher m = pattern.matcher(cell.getStringCellValue());
//                m.find();
//                forItemKeys.add(m.group(1));
//                return m.group(2);
//            case PARAM:
//                Pattern paramParttern = Pattern.compile(CellExpressionType.PARAM.getExpression());
//                Matcher paramMatcher = paramParttern.matcher(cell.getStringCellValue());
//                paramMatcher.find();
//                String group = paramMatcher.group(1);
//                String[] split = group.split("\\.");
//                if (split.length > 1 && forItemKeys.contains(split[0])) {
//                    return null;
//                } else {
//                    return group;
//                }
//
//            case FORMAT_PARAM:
//                Pattern formatParttern = Pattern.compile(CellExpressionType.FORMAT_PARAM.getExpression());
//                Matcher formatMatcher = formatParttern.matcher(cell.getStringCellValue());
//                formatMatcher.find();
//                String group1 = formatMatcher.group(2);
//                String[] split1 = group1.split("\\.");
//                if (split1.length > 0 && forItemKeys.contains(split1[0])) {
//                    return null;
//                } else {
//                    return group1;
//                }
//
//        }
//        return null;
//
//    }
//
//    public static Set<String> getAllForKeys(Workbook wb) {
//        Set<String> keys = Sets.newHashSet();
//        if (wb != null) {
//            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//                Sheet sheet = wb.getSheetAt(i);
//                if (sheet != null) {
//                    for (int n = 0; n < sheet.getLastRowNum(); n++) {
//                        Row row = sheet.getRow(n);
//                        if (isForStartRow(row)) {
//                            Cell cell = row.getCell(0);
//                            String cellValue = cell.getStringCellValue();
//                            Pattern p = Pattern.compile(CellExpressionType.FOR_START.getExpression());
//                            Matcher matcher = p.matcher(cellValue);
//                            if (matcher.find()) {
//                                String group = matcher.group(2);
//                                if (group != null)
//                                    keys.add(group);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return keys;
//
//    }
//
//
//}
