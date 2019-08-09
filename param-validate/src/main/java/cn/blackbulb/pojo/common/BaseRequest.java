package cn.blackbulb.pojo.common;

import java.io.Serializable;
import java.util.Map;

public class BaseRequest implements Serializable {

    private Context context;
    private Map<String,Object> properties;
    private Object obj;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
