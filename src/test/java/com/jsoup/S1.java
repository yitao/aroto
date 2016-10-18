package com.jsoup;

import com.aroto.util.CssFormat;
import com.aroto.util.FileUtils;
import com.aroto.util.RegexUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
//        String url = br + wd;
        String url = "http://www.csdn.net/article/2015-03-24/2824301";
        Connection conn = Jsoup.connect(url);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
        conn.userAgent(userAgent);
        Map<String, String> cookies = new HashMap<>();
        conn.cookies(cookies);
        conn.get();
        Connection.Response res = conn.response();
        Document doc = res.parse();
        String title = "5";
        Elements teles = doc.getElementsByClass("title");
        if(teles.size()>0){
            title = teles.get(0).text().trim();
        }

        Elements eles = doc.getElementsByClass("news_content");
        System.out.println(eles.size());
        if(eles.size()>0){
            StringBuilder sb = new StringBuilder();
            Elements childs = eles.get(0).children();
            for(int i=0;i<childs.size();i++){
                Element child = childs.get(i);
                sb.append(child.text());
                sb.append("\r\n");
            }
            FileUtils.write("d:/tdata/test/"+title+".txt", sb.toString(), false);
        }
//        res.cookies();
//        System.out.println(res.parse().html());
//        FileUtils.write("d:/tdata/test/3.txt", res.parse().html(), false);
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
