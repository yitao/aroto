package com.core;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信样式 rem 值 翻倍
 * Created by yitao on 2016/9/22.
 */
public class WxssMaker {
    String root = "D:\\projects\\bizz\\cards";
//    String suf = ".wxss";
    String suf = ".css";
    static List<String> ig = new ArrayList<>();
    static{
//        ig.add("app.wxss");
//        ig.add("center.wxss");
//        ig.add("center1.wxss");
//        ig.add("index.wxss");
//        ig.add("message_center.wxss");
//        ig.add("offline_card_ocr.wxss");
//        ig.add("seach_company.wxss");
    }



    String test = "D:\\temp";
    @Test
    public void deal() throws IOException{
        File rootDir = new File(root);
//        File testDir = new File(test);
        core(rootDir);
    }

    public void core(File dir) throws IOException{
        FileFilter filter = new SuffixFileFilter(suf);
        File[] files = dir.listFiles(filter);
        for(File file : files){
            if(file.isDirectory()){
            }else{
                if(ig.contains(file.getName())){
                    continue;
                }
                System.out.println("正在处理："+file.getName());
                deal(file);
            }
        }
        for(File file: dir.listFiles()){
            if(file.isDirectory()){
                core(file);
            }
        }
    }



    public void deal(File file) throws IOException{
        StringBuffer buf = new StringBuffer();
        String temp;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while((temp=br.readLine())!=null){
            temp = dealLine(temp);
            buf.append(temp);
            buf.append("\r\n");
        }
        br.close();
        String content = buf.toString();
//        System.out.println(content);
        writeFile(file,content);
    }

    public void writeFile(File file , String content) throws IOException{
        PrintWriter pw = new PrintWriter(file);
        pw.print(content);
        pw.close();
    }

    public String dealLine(String line){
        String reg2= "(:|: *)(\\S*?)rem";
        Pattern p = Pattern.compile(reg2);
        Matcher m = p.matcher(line);
        while(m.find()){
            String re = m.group(2).trim();
            double vb = Double.parseDouble(re)*0.6;
            line=line.replaceAll((re+"rem"),(vb+"rem"));
        }
        return line;
    }


//    @Test
    public void testReg(){
        String line=null;
        line = ":  rem ";
        String reg2= "(: *|:)";
        Pattern p = Pattern.compile(reg2);
        Matcher m = p.matcher(line);
        while(m.find()){
            System.out.println(1);
        }
        String tar2 = "line-height:3.545rem;";
        String tar3 = "line-height: 3.545rem 0;";
        String tar4 = "line-height: 0 0.5rem 0 0.5rem;";
        String tar5 = "line-height: 3.545rem 0.2rem 4.5rem 5rem;";
        line = dealLine(tar2);
        System.out.println(line);
        line = dealLine(tar3);
        System.out.println(line);
        line = dealLine(tar4);
        System.out.println(line);
        line = dealLine(tar5);
        System.out.println(line);

    }



}
