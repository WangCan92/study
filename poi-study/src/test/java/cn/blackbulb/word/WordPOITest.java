package cn.blackbulb.word;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcan
 */
public class WordPOITest {

    @Test
    public void wordPOI(){
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据

        parametersMap.put("names","所有人零零落落");
        List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1=new HashMap<>();
        map1.put("name", "张三");
        map1.put("age", "23");
        map1.put("email", "12121@qq.com");

        Map<String, Object> map2=new HashMap<>();
        map2.put("name", "李四");
        map2.put("age", "45");
        map2.put("email", "45445@qq.com");

        Map<String, Object> map3=new HashMap<>();
        map3.put("name", "Tom");
        map3.put("age", "34");
        map3.put("email", "6767@qq.com");

        table1.add(map1);
        table1.add(map2);
        table1.add(map3);
        wordDataMap.put("students",table1);
        wordDataMap.put("parametersMap",parametersMap);

        File file = new File("/Users/bjqxdn0814/Desktop/合同模板2.doc");//改成你本地文件所在目录

        try {
            // 读取word模板
            FileInputStream fileInputStream = new FileInputStream(file);
            WordTemplate template = new WordTemplate(fileInputStream);

            // 替换数据
            template.replaceDocument(wordDataMap);

            //生成文件
            File outputFile=new File("/Users/bjqxdn0814/Desktop/输出.docx");//改成你本地文件所在目录
            FileOutputStream fos  = new FileOutputStream(outputFile);
            template.getDocument().write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void remvoeBody()throws IOException{
        FileInputStream is = new FileInputStream("/Users/bjqxdn0814/Desktop/第一段.docx");
        FileOutputStream os = new FileOutputStream("/Users/bjqxdn0814/Desktop/out.docx");
        XWPFDocument wordDocument = WordTemplateEngine.createWordDocument(is);
        WordTemplateEngine.removeBody(wordDocument,1);
        WordTemplateEngine.writeDocument(wordDocument,os);
    }
    @Test
    public void addBody()throws IOException{
        FileInputStream is = new FileInputStream("/Users/bjqxdn0814/Desktop/第一段.docx");
        FileOutputStream os = new FileOutputStream("/Users/bjqxdn0814/Desktop/out.docx");
        XWPFDocument wordDocument = WordTemplateEngine.createWordDocument(is);
        XWPFParagraph paragraph = wordDocument.getParagraphs().get(0);
        XmlCursor xmlCursor = paragraph.getCTP().newCursor();
        XWPFParagraph paragraph1 = wordDocument.insertNewParagraph(xmlCursor);
        paragraph1.createRun().setText("新插入一个段落试试！！！！！！");
        xmlCursor = paragraph.getCTP().newCursor();
        XWPFParagraph paragraph2 = wordDocument.insertNewParagraph(xmlCursor);
        paragraph2.createRun().setText("新插入一个段落2222222222");

        WordTemplateEngine.writeDocument(wordDocument,os);
        is.close();
        os.close();
    }

    @Test
    public void subList(){
        List<String> l = Lists.newArrayList();
        l.add("1");//0
        l.add("2");//1
        l.add("3");//2
        l.subList(1,2).forEach(System.out::println);
    }
}
