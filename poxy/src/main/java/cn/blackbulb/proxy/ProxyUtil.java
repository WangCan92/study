package cn.blackbulb.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wangcan
 */
public class ProxyUtil {
    public static Object getInstance(Object target) {
        Object proxy = null;
        Class targetInfo = target.getClass().getInterfaces()[0];
        Method methods[] = targetInfo.getDeclaredMethods();
        String line = "\n";
        String tab = "\t";
        String infName = targetInfo.getSimpleName();
        String content = "";
        String packageContent = "package cn.blackbulb;" + line;
        String importContent = "import " + targetInfo.getName() + ";" + line;
        String clazzFirstLineContent = "public class $Proxy implements " + infName + "{" + line;
        String filedContent = tab + "private " + infName + " target;" + line;
        String constructorContent =
                tab + "public $Proxy (" + infName + " target){" + line + tab + tab + "this.target=target;" + line + tab + "}" + line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            Class args[] = method.getExceptionTypes();
            String argsContent = "";
            String paramsContent = "";
            int flag = 0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();
                argsContent += temp + " p" + flag + ",";
                paramsContent += "p" + flag + ",";
                flag++;
            }
            if (argsContent.length() > 0) {
                argsContent = argsContent.substring(0, argsContent.lastIndexOf(",") - 1);
                paramsContent = paramsContent.substring(0, paramsContent.lastIndexOf(",") - 1);
            }

            methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") {" + line + tab + tab
                    + "System.out.println(\"手写代理！！！！！！\");" + line + tab + tab + "target." + methodName + "(" + paramsContent + ");" + line + tab
                    + "}" + line;
        }
        content = packageContent + importContent + clazzFirstLineContent + filedContent + constructorContent + methodContent + "}";
        File file = new File("/Users/bjqxdn0814/Documents/cn/blackbulb/$Proxy.java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            URL[] urls = new URL[] {new URL("file:/Users/bjqxdn0814/Documents/")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("cn.blackbulb.$Proxy");

            Constructor constructor = clazz.getConstructor(targetInfo);

            proxy = constructor.newInstance(target);
//            //clazz.newInstance();
            //Class.forName()
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proxy;
    }
}
