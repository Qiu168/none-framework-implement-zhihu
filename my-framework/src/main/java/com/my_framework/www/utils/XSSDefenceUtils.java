package com.my_framework.www.utils;

/**
 * @author 14629
 */
public class XSSDefenceUtils {
    public static String xssFilter(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder filtered = new StringBuilder(input.length());
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                case '"':
                    filtered.append("&quot;");
                    break;
                case '\'':
                    filtered.append("&#x27;");
                    break;
                case '/':
                    filtered.append("&#x2F;");
                    break;
                default:
                    filtered.append(c);
                    break;
            }
        }
        return filtered.toString();
    }

}