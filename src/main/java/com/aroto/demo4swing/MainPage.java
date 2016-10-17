package com.aroto.demo4swing;

import sun.applet.Main;
import sun.awt.Win32GraphicsConfig;
import sun.java2d.opengl.WGLGraphicsConfig;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 1 quick start
 * 2 主窗口，长宽，标题，图标，坐标
 * 3 主窗口事件
 * 3.1 页面重绘
 * 3.2 页面切换
 * 4 布局
 * 5 组件
 * 6 布局，组件事件
 * 7 自定义组件，布局
 *
 *
 * 10 数学计算器，四则，弦切，方幂，微积分
 * 10.1 化学方程式
 * 10.2 物理方程式
 * 10.3 生物方程式
 * 11 拼图游戏
 * 12 扫雷游戏
 * 13 蛇吃蛋
 * 14 俄罗斯方块
 * Created by yitao on 2016/9/30.
 */
public class MainPage extends JFrame {
    private String[] menuItems = new String[]{"主窗口"};
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
        CardLayout layout = new CardLayout();
        this.getContentPane().setLayout(layout);//设置布局控制器
        this.setTitle("demo");//设置窗口标题
        this.setFocusable(false);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//一定要设置关闭
        this.setVisible(true);

        this.loaded = true;
    }

}
