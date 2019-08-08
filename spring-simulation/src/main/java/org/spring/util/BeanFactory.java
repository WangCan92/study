package org.spring.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.annotations.MyAutowired;
import org.spring.exception.MySpringException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangcan
 */
public class BeanFactory {
    private volatile ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    public BeanFactory(String xml) {
        parseXML(xml);
    }

    public void parseXML(String xml) {
        try {
            File file = new File(this.getClass().getResource("/").getPath() + "//" + xml);

            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();
            String defaultAutowire = root.attributeValue("default-autowire");
            boolean flag = false;
            if (defaultAutowire != null) {
                flag = true;
            }
            for (Iterator<Element> it = root.elementIterator("bean"); it.hasNext(); ) {
                Element bean = it.next();
                Attribute id = bean.attribute("id");
                Attribute classNameAttribute = bean.attribute("class");
                Class clazz = Class.forName(classNameAttribute.getValue());

                Object obj = null;
                if (bean.elements("constructor-arg") != null && bean.elements("constructor-arg").size() > 0) {
                    for (Iterator<Element> cit = bean.elementIterator("constructor-arg"); cit.hasNext(); ) {
                        Element cArg = cit.next();
                        String fieldName = cArg.attribute("name").getValue();
                        Field field = clazz.getDeclaredField(fieldName);
                        String refName = cArg.attributeValue("ref");
                        Object injectObj = map.get(refName);
                        //                        Class injectClazz = injectObj.getClass();
                        Constructor constructor = clazz.getConstructor(field.getType());
                        obj = constructor.newInstance(injectObj);

                    }
                } else if (bean.elements("property") != null && bean.elements("property").size() > 0) {

                    obj = clazz.newInstance();
                    for (Iterator<Element> pit = bean.elementIterator("property"); pit.hasNext(); ) {
                        Element property = pit.next();
                        String refName = property.attributeValue("ref");
                        String fieldName = property.attribute("name").getValue();
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(obj, map.get(refName));

                    }
                } else if (flag) {
                    //自动装配
                    obj = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        MyAutowired annotation = field.getAnnotation(MyAutowired.class);
                        if (annotation != null) {
                            if (defaultAutowire.equals("byType")) {
                                int count = 0;
                                Object ibean = null;
                                Class fieldType = field.getType();
                                for (String key : map.keySet()) {
                                    Object temp =  map.get(key);
                                    if (temp.getClass().getInterfaces()[0].getName().equals(fieldType.getName())) {
                                        ibean = map.get(key);
                                        count++;
                                    }
                                }
                                if (count > 1) {
                                    throw new MySpringException("只需要一个，获取到多个bean");
                                }
                                field.setAccessible(true);
                                field.set(obj, ibean);

                            }
                        }

                    }
                } else {
                    obj = clazz.newInstance();
                }
                map.put(id.getValue(), obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanId) {
        return map.get(beanId);
    }

}
