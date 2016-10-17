package com.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitao on 2016/10/17.
 */
public class S1 {

    @Test
    public void t1() throws IOException{
        String br = "https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=";
        String wd = "uuid";
        String url = br+wd;
        Connection conn = Jsoup.connect(url);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
        conn.userAgent(userAgent);
        Map<String,String> cookies = new HashMap<>();
        conn.cookies(cookies);
        conn.get();
        Connection.Response res = conn.response();
        res.cookies();
        System.out.println(res.parse().html());
    }

}
