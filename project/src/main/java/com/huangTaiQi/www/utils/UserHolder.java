package com.huangTaiQi.www.utils;

import com.huangTaiQi.www.model.dto.UserDTO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 本来使用tl的但是，存进去之后取不出来，暂时没发现是上面bug所以用map先代替
 * @author 14629
 */
public class UserHolder {
    private static final Map<Integer,UserDTO> TL = new HashMap<>(1);

    public static void saveUser(UserDTO user){

        TL.put(1,user);
    }

    public static UserDTO getUser(){
        return TL.get(1);
    }

    public static void removeUser(){
        TL.remove(1);
    }
}