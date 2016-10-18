package com.jsoup;

import com.aroto.util.CssFormat;
import com.aroto.util.FileUtils;
import com.aroto.util.RegexUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yitao on 2016/10/17.
 */
public class S1 {

    @Test
    public void t1() throws IOException {
        String br = "https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=";
        String wd = "a";
        String url = br + wd;
        Connection conn = Jsoup.connect(url);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
        conn.userAgent(userAgent);
        Map<String, String> cookies = new HashMap<>();
        conn.cookies(cookies);
        conn.get();
        Connection.Response res = conn.response();
        res.cookies();
        System.out.println(res.parse().html());
        FileUtils.write("d:/tdata/test/1.txt", res.parse().html(), false);
    }

    @Test
    public void t2() throws IOException {
        String path = "d:/tdata/test/1.txt";
        String data = "1\r\n";
        FileUtils.write(path, data, false);
        data = "2\r\n";
        FileUtils.write(path, data, true);
    }

    @Test
    public void t3() throws IOException{
        String data = "body{color:#333;background:#fff;padding:6px 0 0;margin:0;position:relative;min-width:900px}body,th,td,.p1,.p2{font-family:arial}p,form,ol,ul,li,dl,dt,dd,h3{margin:0;padding:0;list-style:none}input{padding-top:0;padding-bottom:0;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box}";
        data = CssFormat.formatQuick(data);
        String path = "d:/tdata/test/2.txt";
        FileUtils.write(path, data, false);
    }

    @Test
    public void t4(){
        String data = "   :   ";
        data = data.replaceAll(" +, +"," , ").replaceAll(" +: +"," : ");
        System.out.println("["+data+"]");
    }



}
