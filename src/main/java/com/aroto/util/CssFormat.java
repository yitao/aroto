package com.aroto.util;

/**
 * css 格式化器
 * {和}换行
 * 去除多余换行
 * 去除多余空格
 * ,:左右保留一个空格
 * ｛;左边保留一个空格
 * Created by yitao on 2016/10/18.
 */
public class CssFormat {
    public static String STEP = "\t";
    public static String NLINE = "\r\n";
    public static String genStep(int st){
        String s = "";
        for(int i=0;i<st;i++){
            s+=STEP;
        }
        return s;
    }

    private static String nline(String data){
        return data.replaceAll("\\{","{"+NLINE).replaceAll("\\}",NLINE+"}"+NLINE).replaceAll(";",";"+NLINE);
    }

    private static String keepSpaceLR(String data){
        return data.replaceAll(","," , ").replaceAll(":"," : ").replaceAll(" +, +"," , ").replaceAll(" +: +"," : ");
    }
    private static String keepSpaceL(String data){
        return data.replaceAll("\\{"," {").replaceAll(";"," ;").replaceAll(" +\\{"," {").replaceAll(" +;"," ;");
    }

    public static String formatSafe(String data){
        data = nline(data);
        data = RegexUtils.replaceExNline(data);
        StringBuffer sb = new StringBuffer();
        int st = 0;
        String[] ss = data.split("\\r\\n");
        for(int i=0;i<ss.length;i++){
            String s = ss[i];
            if(s.endsWith("}")){
                st--;
            }
            sb.append(genStep(st));
            s = RegexUtils.replaceExSpace(s);
            s = keepSpaceLR(s);
            s = keepSpaceL(s);
            sb.append(s);
            if(s.endsWith("{")){
                st++;
            }
        }
        return sb.toString();
    }

    public static String formatQuick(String data){
        data = nline(data);
        data = RegexUtils.replaceExNline(data);
        StringBuilder sb = new StringBuilder();
        int st = 0;
        String[] ss = data.split("\\r\\n");
        for(int i=0;i<ss.length;i++){
            String s = ss[i];
            if(s.endsWith("}")){
                st--;
            }
            sb.append(genStep(st));
            s = keepSpaceLR(s);
            s = keepSpaceL(s);
            sb.append(s);
            sb.append(NLINE);
            if(s.endsWith("{")){
                st++;
            }
        }
        return sb.toString();
    }

}
