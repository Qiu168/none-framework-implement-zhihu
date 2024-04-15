package com.huangTaiQi.www.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author _qqiu
 */
public class SensitiveWordConstants {
    private static final String[] SET_VALUE={"反对派","暴力","政治犯","异教徒","假冒宗教","恐怖主义","种族歧视","种族主义","种族隔离",
            "性别歧视","性别偏见","性别歧视"};
    public static final Set<String> SENSITIVE_WORDS=new HashSet<>(Arrays.asList(SET_VALUE));
}
