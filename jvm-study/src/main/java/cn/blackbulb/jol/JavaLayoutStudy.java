package cn.blackbulb.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author wangcan
 */
public class JavaLayoutStudy {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
