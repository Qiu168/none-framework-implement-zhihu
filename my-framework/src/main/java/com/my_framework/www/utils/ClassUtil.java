package com.my_framework.www.utils;


import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 14629
 */
public class ClassUtil {
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(ClassUtil.class.getName());

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载不是懒加载的类，并且初始化它。
     * @param className className
     * @param isInitialized 是否初始化
     * @return class对象
     */
    public static Class<?> loadClass(String className, boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE,"load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }
    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            //得到包名下的资源URL枚举
            //Enumeration枚举接口，用法和Iterator相似，提供了遍历Vector和HashTable类型集合元素的功能，不支持元素的移除操作
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                //file:/E:/MyLife/MyProjects/Eclipse3.2/Database/bin/com/test/icons.gif
                URL url = urls.nextElement();
                if (url != null){
                    //协议
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)){
                        //百度了一下，获取文件路径时，里面的路径空格会被"%20"代替,因此要重新替换成空格
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)){
                        //JarURLConnection的实例可以引用一个JAR的压缩包或者这种包里的某个文件
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }
    /**
     * 加载类
     * @param classSet class文件的set。
     * 在Java中，类的全限定名是唯一的，因此可以使用Set来保证集合中的元素不会重复。如果使用List，可能会导致同一个类被加入集合多次。
     * @param packagePath 是指包路径，即包在文件系统中的路径，比如com/example/util。
     * @param packageName 是指包名，即Java代码中声明的包名，比如com.example.util。
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName){
        //把该包路径下的全部.class文件与文件夹加入到files数组里
        //取指定包名下所有的文件和文件夹
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        //遍历文件数组
        assert files != null;
        for (File file : files){
            String fileName = file.getName();
            //如果是.class文件，则直接加载它
            if (file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            }
            //如果是文件夹，则进行递归操作
            else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 加载类(但不初始化)
     * 使用 loadClass() 方法获得的 Class 对象只完成了类加载过程中的第一步：加载，后续的操作均未进行。
     * 使用 Class.forName() 方法获得 Class 对象是已经执行完初始化的了
     * 并且把类加入到Set中
     */
    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

}
