package com.huangTaiQi.www.utils;

import com.huangTaiQi.www.model.dto.UserDTO;

import java.util.Map;


/**
 * @author 14629
 */
public class UserHolder {
    /**
     * 存User
     */
    private static final ThreadLocal<UserDTO> USR_INFO = new ThreadLocal<>();
    private static final ThreadLocal<Map<Integer, Boolean>> USER_RIGHT = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        USR_INFO.set(user);
    }
    public static void saveUserRight(UserDTO user){
        USR_INFO.set(user);
    }
    public static UserDTO getUser(){
        return USR_INFO.get();
    }
    public static Map<Integer,Boolean> getUserRight(){
        return USER_RIGHT.get();
    }

    public static void removeUser(){
        USR_INFO.remove();
    }
    public static void removeUserRight(){
        USER_RIGHT.remove();
    }
}