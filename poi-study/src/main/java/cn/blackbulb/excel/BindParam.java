package cn.blackbulb.excel;

import java.util.List;
import java.util.Map;

/**
 * @author wangcan
 */
public class BindParam {
//    普通参数
    private Map<String,Object> commonParam;
//    循环参数
    private Map<String,List<Map<String,Object>>> arrayParam;

    public Map<String, Object> getCommonParam() {
        return commonParam;
    }

    public void setCommonParam(Map<String, Object> commonParam) {
        this.commonParam = commonParam;
    }

    public Map<String, List<Map<String, Object>>> getArrayParam() {
        return arrayParam;
    }

    public void setArrayParam(Map<String, List<Map<String, Object>>> arrayParam) {
        this.arrayParam = arrayParam;
    }
}
