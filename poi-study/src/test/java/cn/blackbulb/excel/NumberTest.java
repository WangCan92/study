package cn.blackbulb.excel;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcan
 */
public class NumberTest {
    public static String forStartKey = "(\\w*)\\.(\\w*)";
    public static String ORDERBY = "##(\\w+)(.(\\w+))?( (asc|desc))?##";
//    public static String forstart = "\\{for (\\w*) in ((=>(\\w*)\\.(\\w*))*)( (order by( ((\\w*)(.(\\w*))?( (aes|desc))?))*))?\\}";

    @Test
    public void test() {
        String s = "sdfsdfdddd";
        String[] split = s.split("\\.");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }

    @Test
    public void forStartTest() {
        Pattern p = Pattern.compile("\\{for (\\w+) in ((=>(\\w+)\\.([\\u4e00-\\u9fa5_a-zA-Z0-9]+))+)( (order by(##((\\w+)(.(\\w*))?( (asc|desc))?)##)+))?\\}");
        String s = "{for item1 in =>Object_kryb__u.Field_4ya6__u order by##Field_hu33__u desc####aa##}";
//        String s ="{for item in =>WORKFLOW.测试选择并行}";
        Matcher matcher = p.matcher(s);
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group" + i + ":" + matcher.group(i));
            }
        }
        String group = matcher.group(7);
        Pattern orderBy = Pattern.compile("##(\\w+)(.(num|equivalentMoney))?( (asc|desc))?##");
        Matcher matcher1 = orderBy.matcher(group);
        System.out.println("======================"+group+"=========================");
        while (matcher1.find()) {
            System.out.println("--------------------------------------------------");
            for (int i = 0; i < matcher1.groupCount() + 1; i++) {
                System.out.println("group" + i + ":" + matcher1.group(i));
            }
            System.out.println("--------------------------------------------------");
        }

    }

    @Test
    public void pTest() {
        String key = "{for item in =>compan_y.field1}";
        Pattern p = Pattern.compile("\\{for (\\w*) in ((=>(\\w*)\\.(\\w*))*)\\}");
        Matcher matcher = p.matcher(key);
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {

                System.out.println("group" + i + "：" + matcher.group(i));
            }
        }
    }

    @Test
    public void commonTest() {
        Pattern p = Pattern.compile("\\{(sum|count|average|min|max)#((=>(\\w*)(\\.(\\w*))?)*)\\}");
        String s = "{count#=>compan_y.filesdf}";
        //        String s ="{for item in companys}";
        Matcher matcher = p.matcher(s);
        if (matcher.find()) {
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println("group" + i + ":" + matcher.group(i));
            }
        }
    }

    @Test
    public void ParamTest() {
        Pattern p = Pattern.compile("\\{([\\w.]*)\\}");
        Matcher matcher = p.matcher("{api1}asdf{api2}");
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println("group" + i + ":" + matcher.group(i));
            }
        }
    }

    @Test
    public void isNumberTest() {
        String s = "-123.12";
        String reg = "^[0-9]+(.[0-9]+)?$";
        System.out.println(s.matches(reg));

    }

    @Test
    public void scheduleTest() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            System.out.println("77777777777");
        }, 5, TimeUnit.SECONDS);
    }

    @Test
    public void workFlowTest() {
        Pattern pattern = Pattern.compile(CellExpressionType.FOR_START.getExpression());
        Matcher matcher = pattern.matcher("{for item in =>WORKFLOW.dddd}");
        if (matcher.find()) {
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println("group" + i + ":" + matcher.group(i));
            }
        }
    }

    @Test
    public void setTest() {
        Pattern pattern = Pattern.compile("##(\\w)##");
        Matcher matcher = pattern.matcher("##pp##");
        if (matcher.find()) {
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println("group"+i+":"+matcher.group(i));
            }
        }
    }

}
