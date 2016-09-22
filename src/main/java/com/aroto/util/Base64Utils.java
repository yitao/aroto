package com.aroto.util;

import net.iharder.Base64;

import java.io.IOException;

/**
 * Created by yitao on 2016/9/8.
 */
public class Base64Utils{

    public final static byte[] decode(String key) throws IOException{
        return Base64.decode(key.getBytes());
    }

}
