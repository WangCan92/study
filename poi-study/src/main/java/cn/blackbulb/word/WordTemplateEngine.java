package cn.blackbulb.word;

import cn.blackbulb.excel.BindParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author wangcan
 */
public class WordTemplateEngine {
    /**
     * 创建word文档
     */
    public static XWPFDocument createWordDocument(InputStream is) throws IOException {
        XWPFDocument document = new XWPFDocument(is);
        return document;
    }

    /**
     * word写文件
     */
    public static void writeDocument(XWPFDocument dc, OutputStream os) throws IOException {
        dc.write(os);
    }

    /**
     * word删除
     */
    public static void removeBody(XWPFDocument dc, int i) {
        List<IBodyElement> bodyElements = dc.getBodyElements();
        if (CollectionUtils.isEmpty(bodyElements) || bodyElements.size() < i || i < 0) {
            return;
        }
        dc.removeBodyElement(i);
    }

    /**
     * 在指定位置添加一个表格
     * @param dc
     * @param i
     * @return
     */
    public static XWPFTable addTable(XWPFDocument dc, int i) {
        if (dc.getBodyElements().size() < i) {
            return dc.createTable();
        } else {
            IBodyElement iBodyElement = dc.getBodyElements().get(i);
            XmlCursor xmlCursor = null;
            switch (iBodyElement.getElementType()) {
                case PARAGRAPH:
                    xmlCursor = ((XWPFParagraph) iBodyElement).getCTP().newCursor();
                    break;
                case TABLE:
                    xmlCursor = ((XWPFTable) iBodyElement).getCTTbl().newCursor();
                    break;
            }
            if (xmlCursor != null) {
                return dc.insertNewTbl(xmlCursor);
            }
        }
        return null;
    }

    /**
     * 指定位置添加段落
     * @param dc
     * @param i
     * @return
     */
    public static XWPFParagraph addParagraph(XWPFDocument dc,int i){
        if (dc.getBodyElements().size() < i) {
            return dc.createParagraph();
        } else {
            IBodyElement iBodyElement = dc.getBodyElements().get(i);
            XmlCursor xmlCursor = null;
            switch (iBodyElement.getElementType()) {
                case PARAGRAPH:
                    xmlCursor = ((XWPFParagraph) iBodyElement).getCTP().newCursor();
                    break;
                case TABLE:
                    xmlCursor = ((XWPFTable) iBodyElement).getCTTbl().newCursor();
                    break;
            }
            if (xmlCursor != null) {
                return dc.insertNewParagraph(xmlCursor);
            }
        }
        return null;
    }

    public static void copyRun(XWPFRun target, XWPFRun source) {
        target.getCTR().setRPr(source.getCTR().getRPr());
        target.setText(source.text());
    }

    public static void copyParagraph(XWPFParagraph target, XWPFParagraph source) {
        //设置样式
        target.getCTP().setPPr(source.getCTP().getPPr());
        //移除目标段落中所有的run
        for (int i = target.getRuns().size(); i >= 0; i--) {
            target.removeRun(i);
        }
        //copy run
        for (XWPFRun xwpfRun : source.getRuns()) {
            XWPFRun run = target.createRun();
            copyRun(run, xwpfRun);
        }
    }

    public static void copyTableCell(XWPFTableCell target, XWPFTableCell source) {
        //设置单元格属性
        if (source.getCTTc() != null) {
            target.getCTTc().setTcPr(source.getCTTc().getTcPr());
        }
        //删除单元格中原有内容
        for (int pos = 0; pos < target.getParagraphs().size(); pos++) {
            target.removeParagraph(pos);
        }
        // 添加段落
        for (XWPFParagraph sp : source.getParagraphs()) {
            XWPFParagraph targetP = target.addParagraph();
            copyParagraph(targetP, sp);
        }
    }

    public static void copyTableRow(XWPFTableRow target, XWPFTableRow source) {
        if (source.getCtRow() != null) {
            target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        }
        // 复制单元格
        for (int i = 0; i < source.getTableCells().size(); i++) {
            XWPFTableCell cell1 = target.getCell(i);
            XWPFTableCell cell2 = source.getCell(i);
            if (cell1 == null) {
                cell1 = target.addNewTableCell();
            }
            copyTableCell(cell1, cell2);
        }

    }

    public static void paragraphBindingParams(XWPFParagraph paragraph,BindParam bindParam){
        String text = paragraph.getText();
//        Pattern p = Pattern.compile(EX)
    }

}
