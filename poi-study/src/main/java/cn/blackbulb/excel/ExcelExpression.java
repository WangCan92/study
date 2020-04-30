package cn.blackbulb.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcan
 */
public class ExcelExpression {
    public static final String EXCEL_FOR_EXPRESSION_PATTERN_START="\\{for ([\\w.]*) in ([\\w.]*)\\}";
    public static final String EXCEL_FOR_EXPRESSION_PATTERN_PARAM="\\{([\\w.]*)\\}";
    public static final String EXCEL_FOR_EXPRESSION_PATTERN_END="\\{end for\\}";
    private String expression;

    public static void main(String[] args) {
        /*Pattern p = Pattern.compile(EXCEL_FOR_EXPRESSION_PATTERN_START);
        String s = "{for item in students}";
        Matcher matcher = p.matcher(s);
        boolean b = matcher.find();
        System.out.println("匹配结果"+b);
        if (b){
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }*/

        Pattern p = Pattern.compile(EXCEL_FOR_EXPRESSION_PATTERN_PARAM);
        String s = "{students.name}";
        Matcher matcher = p.matcher(s);
        boolean b = matcher.find();
        System.out.println("匹配结果"+b);
        if (b){
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
        }

    }

}
