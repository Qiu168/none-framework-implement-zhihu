package com.huangTaiQi.www.utils;

import com.huangTaiQi.www.model.dto.UserDTO;
import com.my_framework.www.security.RightGet;

import java.util.Map;


/**
 * @author 14629
 */
public class UserHolder implements RightGet {
    /**
     * 存User
     */
    private static final ThreadLocal<UserDTO> USR_INFO = new ThreadLocal<>();
    private static final ThreadLocal<Map<Long, Boolean>> USER_RIGHT = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        USR_INFO.set(user);
    }
    public static void saveUserRight(Map<Long, Boolean> right){
        USER_RIGHT.set(right);
    }
    public static UserDTO getUser(){
        return USR_INFO.get();
    }
    public static Map<Long,Boolean> getUserRightMap(){
        return USER_RIGHT.get();
    }
    public static void removeUser(){
        USR_INFO.remove();
    }
    public static void removeUserRight(){
        USER_RIGHT.remove();
    }

    @Override
    public Boolean getRight(Long rightName) {
        return USER_RIGHT.get().getOrDefault(rightName,false);
    }
}