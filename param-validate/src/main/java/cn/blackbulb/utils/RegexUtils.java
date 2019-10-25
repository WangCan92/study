package cn.blackbulb.utils;

import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.regex.Pattern;

/**
 * @Description: 正则校验工具
 * @Author: wangxinyu
 * @Email: wangxinyu@jingdata.com
 * @Create: 2018-07-13 11:17
 **/
public class RegexUtils {

    //    private static final Pattern EMAIL_PATTERN =
    //            Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    //
    //    private static final Pattern PHONE_PATTERN =
    //            Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[0-9]))\\d{8}$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9+\\-;,]{11}$");

    /**
     * 判断是否是邮箱格式
     */
    public static boolean isEmail(String string) {
        if (string == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(string).matches();
    }

    /**
     * 判断是否是手机号
     */
    public static boolean isPhone(String phone) {
        if (phone == null || phone.length() == 0 || phone.length() != 11) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

}
