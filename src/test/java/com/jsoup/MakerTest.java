package com.jsoup;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yitao on 2016/9/22.
 */
public class MakerTest {
    String reg= "https://mp.weixin.qq.com/debug/wxadoc/dev/api/(.*)\\?(.*)";
    String reg2= "https://mp.weixin.qq.com/debug/wxadoc/dev/api/(.*)\\?(.*)#(.*)";
    String root = "D:\\doc\\小程序开发文档";
    String tarRoot = "D:\\doc\\小程序开发文档1";
    String suf = ".htm";

    String preMatch="https://mp.weixin.qq.com/debug/wxadoc/dev/";
    String sufMatch="/";
    String mk = " · MINA.htm";

    @Test
    public void test() throws IOException{
        Set<String> hrefSet = new HashSet<>();

        File tarRootDir = new File(tarRoot);
        if(!tarRootDir.exists()) {
            tarRootDir.mkdirs();
        }
        File rootDir = new File(root);
        if(rootDir.isDirectory()){
            FileFilter filter = new SuffixFileFilter(suf);
            File[] files = rootDir.listFiles(filter);
            for(File file : files){
                Document document = Jsoup.parse(file,"utf-8");
                Elements navEs = document.getElementsByClass("summary");
                Element nav = navEs.first();
                if(nav!=null) {
                    Elements es = nav.children();
                    for (int i = 0; i < es.size(); i++) {
                        Element li = es.get(i);
                        Element e = li.child(0);
                        if(e!=null) {
                            String href = e.attr("href");
                            String text = e.ownText();
                            File f = new File(rootDir, text + mk);
                            String fn=null;//文件名
                            String ta=null;//文件名
                            Pattern p2 = Pattern.compile(reg2);
                            Matcher m2 = p2.matcher(href);
                            boolean mc2 = m2.matches();
                            if(mc2){
                                fn = m2.group(1);
                                ta = m2.group(3);
                            }else{
                                Pattern p = Pattern.compile(reg);
                                Matcher m = p.matcher(href);
                                boolean mc = m.matches();
                                if(mc){
                                    fn=m.group(1);
                                }
                            }
                            System.out.println(fn+"#"+ta);
                            System.out.println(href + " " + text + " " + f.exists());
                            if(!hrefSet.contains(href) && f.exists() && fn!=null){
                                File tf = new File(tarRootDir,fn);
                                if(!tf.exists()){
                                    tf.createNewFile();
                                }
                                String content = replaceHtmlFromFile(f);
                                //System.out.println(content);
                                if(content!=null) {
                                    writeFile(tf,content);
                                }
                            }
                            hrefSet.add(href);
                        }
                        Element ce = li.child(1);
                        if(ce!=null){
                            Elements ces = ce.getElementsByTag("a");
                            for (int j = 0; j < ces.size(); j++) {
                                Element e2 = ces.get(j);
                                String href = e2.attr("href");
                                String text = e2.ownText();
                                File f = new File(rootDir, text + mk);
                                String fn=null;//文件名
                                String ta=null;//文件名
                                Pattern p2 = Pattern.compile(reg2);
                                Matcher m2 = p2.matcher(href);
                                boolean mc2 = m2.matches();
                                if(mc2){
                                    fn = m2.group(1);
                                    ta = m2.group(3);
                                }else{
                                    Pattern p = Pattern.compile(reg);
                                    Matcher m = p.matcher(href);
                                    boolean mc = m.matches();
                                    if(mc){
                                        fn=m.group(1);
                                    }
                                }
                                System.out.println(fn+"#"+ta);
                                System.out.println("\t" + href + " " + text + " " + f.exists());
                                if(!hrefSet.contains(href) && f.exists() && fn!=null){
                                    File tf = new File(tarRootDir,fn);
                                    if(!tf.exists()){
                                        tf.createNewFile();
                                    }
                                    String content = replaceHtmlFromFile(f);
                                    //System.out.println(content);
                                    if(content!=null) {
                                        writeFile(tf,content);
                                    }
                                }
                                hrefSet.add(href);
                            }
                        }
                    }
                }
//                System.out.println(document.html());
                break;
            }
        }
    }

    public String replaceHtmlFromFile(File file) throws IOException{
        String content = "";
        Document document = Jsoup.parse(file,"utf-8");
        Elements navEs = document.getElementsByClass("summary");
        Element nav = navEs.first();
        if(nav!=null) {
            Elements es = nav.children();
            for (int i = 0; i < es.size(); i++) {
                Element li = es.get(i);
                Element e = li.child(0);
                if(e!=null) {
                    String href = e.attr("href");
                    String text = e.ownText();
                    String fn=null;//文件名
                    String ta=null;//文件名
                    Pattern p2 = Pattern.compile(reg2);
                    Matcher m2 = p2.matcher(href);
                    boolean mc2 = m2.matches();
                    if(mc2){
                        fn = m2.group(1);
                        ta = m2.group(3);
                    }else{
                        Pattern p = Pattern.compile(reg);
                        Matcher m = p.matcher(href);
                        boolean mc = m.matches();
                        if(mc){
                            fn=m.group(1);
                        }
                    }
                    String nf = fn/*+"#"+(ta==null?"":ta)*/;
                    System.out.println(nf);
                    if(fn!=null){
                        e.attr("href",nf);
                    }
                }
                Element ce = li.child(1);
                if(ce!=null){
                    Elements ces = ce.getElementsByTag("a");
                    for (int j = 0; j < ces.size(); j++) {
                        Element e2 = ces.get(j);
                        String href = e2.attr("href");
                        String text = e2.ownText();
                        String fn=null;//文件名
                        String ta=null;//文件名
                        Pattern p2 = Pattern.compile(reg2);
                        Matcher m2 = p2.matcher(href);
                        boolean mc2 = m2.matches();
                        if(mc2){
                            fn = m2.group(1);
                            ta = m2.group(3);
                        }else{
                            Pattern p = Pattern.compile(reg);
                            Matcher m = p.matcher(href);
                            boolean mc = m.matches();
                            if(mc){
                                fn=m.group(1);
                            }
                        }
                        String nf = fn/*+"#"+ta*/;
                        System.out.println("\t"+nf);
                        if(fn!=null){
                            e2.attr("href",nf);
                        }
                        System.out.println(fn+"#"+ta);
                    }
                }
            }
        }
        content = document.html();
        return content;
    }

    public void writeFile(File file , String content) throws IOException{
        PrintWriter pw = new PrintWriter(file);
        pw.print(content);
        pw.close();
    }

    @Test
    public void testReg (){
        String tar = "https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-network.html?t=1474461645804";
        String tar2 = "https://mp.weixin.qq.com/debug/wxadoc/dev/api/network-socket.html?t=1474461645804#wxconnectsocketobject";
        String reg= "https://mp.weixin.qq.com/debug/wxadoc/dev/api/(.*)\\?(.*)";
        String reg2= "https://mp.weixin.qq.com/debug/wxadoc/dev/api/(.*)\\?(.*)#(.*)";
        Pattern p = Pattern.compile(reg2);
        Matcher m = p.matcher(tar2);
        boolean mc = m.matches();
        if(mc){
            System.out.println("---------------------");
            System.out.println("["+m.group(1)+"]["+m.group(2)+"]["+m.group(3)/*+"]["+m.group(4)*/+"]");
            System.out.println("---------------------");
        }
    }

    /*    @Test
    public void delFile(){
        String d = "D:\\doc\\1";
        File rootDir = new File(d);
        FileFilter filter = new SuffixFileFilter(".htm");
        File[] files = rootDir.listFiles(filter);
        for(File file : files){
            file.delete();
        }
    }*/
}
