package com.my_framework.www.core.context.Impl;

import com.my_framework.www.core.context.ResourceLoader;
import com.my_framework.www.core.context.ResourceName;
import com.my_framework.www.valid.annotation.Nullable;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author _qiu
 */
public class DefaultResourceLoader implements ResourceLoader {
    private final Map<?, ?> resources;
    private final Map<?, ?> active;

    /**
     * false是yaml
     * true是properties
     */
    @Getter
    boolean fileType = false;

    public DefaultResourceLoader() {
        Map<?, ?> application = readApplicationYaml(null);
        if (application == null) {
            application = readApplicationProperties(null);
        }
        if (application == null) {
            application = new HashMap<>();
        }
        resources = application;
        String value = getValue(ResourceName.ACTIVE);
        Map<?, ?> active=null;
        if(value!=null){
            active=readApplicationYaml(value);
            if(active==null){
                active=readApplicationProperties(value);
            }
        }
        this.active=active;
    }

    public Map<?, ?> getResource() {
        return resources;
    }

    @SneakyThrows//todo
    private Map<?, ?> readApplicationProperties(@Nullable String active) {
        String fileName;
        if(active==null){
            fileName ="application.properties";
        }else{
            fileName="application-"+active+".properties";
        }
        if (resourceAbsent(fileName)) {
            return null;
        }
        InputStream inputStream = this.getClass().getResourceAsStream("/"+fileName);
        Properties properties = new Properties();
        properties.load(inputStream);

        return properties;
    }

    @SneakyThrows
    private Map<?, ?> readApplicationYaml(@Nullable String active) {
        String fileName;
        if(active==null){
            fileName ="application.yaml";
        }else{
            fileName="application-"+active+".yaml";
        }
        if (resourceAbsent(fileName)) {
            return null;
        }
        if (resourceAbsent(fileName)) {
            return null;
        }
        fileType = true;
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getResourceAsStream("/"+fileName);
        return yaml.<Map<String, Object>>load(inputStream);
    }

    /**
     * @return 如果为null 返回“null”
     */
    @Nullable
    private String getYamlValue(String[] keys, Map<?, ?> map) {
        Object ans = map;
        for (String key : keys) {
            if (ans == null) {
                return null;
            }
            ans = ((Map<?, ?>) ans).get(key);
        }
        if(ans==null){
            return null;
        }
        return String.valueOf(ans);

    }

    private boolean resourceAbsent(String resourcePath) {
        URL resource = DefaultResourceLoader.class.getClassLoader().getResource(resourcePath);
        return resource == null;
    }

    @Override
    public String getValue(String key) {
        if (fileType) {
            if(active!=null){
                String activeValue = getYamlValue(key.split("\\."), active);
                if(null!=activeValue){
                    return activeValue;
                }
            }
            return getYamlValue(key.split("\\."), resources);
        }
        if(active!=null){
            String activeValue = String.valueOf(active.get(key));
            if(null!=activeValue){
                return activeValue;
            }

        }
        return String.valueOf(resources.get(key));
    }

}
