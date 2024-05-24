package com.my_framework.www.db.pool.config;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author _qqiu
 */
public class DataSourceConfig {
    private String driver;
    private String url;
    private String userName;
    private String passWord;
    private String initSize;
    private String maxSize;
    private String health;
    private String delay;
    private String period;
    private String timeout;
    private String numConnectionsToCreate;

    /**
     * 构造方法，使用配置文件和反射，将成员变量赋值
     */
    public DataSourceConfig(){
        Properties pro=new Properties();
        try {
            pro.load(DataSourceConfig.class.getClassLoader().getResourceAsStream("DBCP.properties"));
            for (Object obj : pro.keySet()) {
                String fieldName=obj.toString();
                Field field = this.getClass().getDeclaredField(fieldName);
                Method method = this.getClass().getMethod(toMethodName(fieldName), field.getType());
                method.invoke(this, pro.get(obj));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String toMethodName(String fieldName){
        char[] chars = fieldName.toCharArray();
        chars[0] -= 32;
        return "set" + new String(chars);
    }

    public String getNumConnectionsToCreate() {
        return numConnectionsToCreate;
    }

    public void setNumConnectionsToCreate(String numConnectionsToCreate) {
        this.numConnectionsToCreate = numConnectionsToCreate;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getInitSize() {
        return initSize;
    }

    public void setInitSize(String initSize) {
        this.initSize = initSize;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", initSize='" + initSize + '\'' +
                ", maxSize='" + maxSize + '\'' +
                ", health='" + health + '\'' +
                ", delay='" + delay + '\'' +
                ", period='" + period + '\'' +
                ", timeout='" + timeout + '\'' +
                '}';
    }
}
