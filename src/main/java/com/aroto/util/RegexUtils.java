package com.aroto.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yitao on 2016/10/18.
 */
public class RegexUtils {

    public static String replaceExSpace(String s){
        return s.replaceAll(" +"," ");
    }

    public static String replaceExNline(String s){
        return s.replaceAll("[\\r\\n]+","\r\n");
    }

}
