package com.aroto.util;

/**
 * Created by yitao on 2016/9/22.
 */
public class ExceptionUtils {
    public ExceptionUtils() {
    }

    public static final RuntimeException unchecked(Exception e) {
        return e instanceof RuntimeException?(RuntimeException)e:new RuntimeException(e.getMessage(), e);
    }
}