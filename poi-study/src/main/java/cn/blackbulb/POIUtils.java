package cn.blackbulb;

import org.apache.poi.ss.usermodel.*;

import java.util.Iterator;

/**
 * @author wangcan
 */
public class POIUtils {
    public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        //边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        //背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        //      toStyle.setFont(fromStyle.getFont(null));
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());//首行缩进
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());//旋转
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    public static void copyCell( Cell srcCell, Cell distCell, boolean copyValueFlag) {
        CellStyle newstyle = distCell.getSheet().getWorkbook().createCellStyle();
        copyCellStyle(srcCell.getCellStyle(), newstyle);
        //        distCell.setEncoding(srcCell.getEncoding());
        //样式
        distCell.setCellStyle(newstyle);
        //评论
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType cellType = srcCell.getCellType();
        distCell.setCellType(cellType);
        //        distCell.setCellType();
        if (copyValueFlag) {
            switch (cellType) {
                case STRING:
                    distCell.setCellValue(srcCell.getRichStringCellValue());
                    break;
                case BOOLEAN:
                    distCell.setCellValue(srcCell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    distCell.setCellValue(srcCell.getNumericCellValue());
                    break;
                case FORMULA:
                    distCell.setCellValue(srcCell.getCellFormula());
                    break;
                default:
                    ;
            }
        }
    }

    public static void copyRow(Row fromRow, Row toRow, boolean copyValueFlag) {
        if(fromRow==null){
            return;
        }
        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tempcell = cellIt.next();
            Cell newCell = toRow.createCell(tempcell.getColumnIndex());
            copyCell(tempcell, newCell, copyValueFlag);
        }
    }

    /**
     * 移除当前行后，所有单元格自动向上移动一行
     * @param sheet
     */
    public static void removeExcelRow(Sheet sheet,int rowNum){
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum<rowNum){
            return;
        }
        Row lastRow = sheet.createRow(sheet.getLastRowNum() + 1);
        sheet.shiftRows(rowNum+1,sheet.getLastRowNum(),-1);
        sheet.removeRow(lastRow);

    }
}
