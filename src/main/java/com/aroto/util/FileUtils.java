package com.aroto.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yitao on 2016/10/18.
 */
public class FileUtils {

    public static void write(String path,String data,boolean append) throws IOException{
        File file = new File(path);
        if(!file.exists()){
            File dir = file.getParentFile();
            dir.mkdirs();
        }
        FileWriter fw = new FileWriter(file,append);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(data);
        pw.close();
    }
}
