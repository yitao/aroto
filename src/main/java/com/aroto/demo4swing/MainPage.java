package com.aroto.demo4swing;

import sun.applet.Main;
import sun.awt.Win32GraphicsConfig;
import sun.java2d.opengl.WGLGraphicsConfig;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yitao on 2016/9/30.
 */
public class MainPage extends JFrame {
    private boolean loaded=false;
    private int width=300;
    private int height=500;

    public MainPage(){
        super();
        //fullScreen();
        init();
    }

    public MainPage(String title,int width, int height){
        this();
        this.setTitle(title);
        this.width = width;
        this.height = height;
    }

    public void location(int x,int y){
        //窗口定位
        this.setLocation(x,y);
    }
    public void fullScreen(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        double sw = dimension.getWidth();
        double sh = dimension.getHeight();
        System.out.println("sw/sh["+sw+"/"+sh+"]");
        this.width = (int) sw;
        this.height = (int) sh;
    }

    public void init(){
        System.out.println("-----------------------------------------");
        System.out.println("---                init               ---");
        this.setSize(this.width,this.height);
        this.getContentPane().setLayout(new FlowLayout());//设置布局控制器
        this.setTitle("demo");//设置窗口标题
        this.setFocusable(false);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//一定要设置关闭
        this.setVisible(true);

        this.loaded = true;
    }


}