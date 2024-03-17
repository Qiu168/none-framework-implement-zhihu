package com.my_framework.www.core.context;

/**
 * @author _qiu
 */
public interface ResourceName {
    String PORT="server.port";
    String ACTIVE="spring.profiles.active";
    static String[] getResourceNames(){
        return new String[]{PORT,ACTIVE};
    }

}
