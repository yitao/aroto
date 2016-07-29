package com.aroto.tuling.core;

import com.alibaba.fastjson.JSONObject;
import com.aroto.util.Aes;
import com.aroto.util.Md5;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitao on 2016/7/29.
 */
public class RobotManager {
    private static final String apiUrl = "http://www.tuling123.com/openapi/api";
    public static final String mu = "mu";
    private static Map<String,Robot> robots;
    static{
        robots = new HashMap<String, Robot>();
    }
    //初始化小沐
    public static Robot mu(){
        Robot muRobot = robots.get(mu);
        if(muRobot==null){
            muRobot = new Robot();
        }
        return muRobot;
    }

    private static String genKey(Robot robot,String timestamp){
        //生成密钥
        String keyParam = robot.getSecret()+timestamp+robot.getApiKey();
        String key = Md5.MD5(keyParam);
        return key;
    }

    private static String genData(Robot robot,String msg,String timestamp){
        String key = genKey(robot,timestamp);
        //待加密的json数据
        String data = "{\"key\":\""+robot.getApiKey()+"\",\"info\":\""+msg+"\"}";
        //加密
        Aes mc = new Aes(key);
        data = mc.encrypt(data);
        return data;
    }

    public static String call(Robot robot,String msg){
        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        String data = genData(robot,msg,timestamp);
        //封装请求参数
        JSONObject json = new JSONObject();
        json.put("key", robot.getApiKey());
        json.put("timestamp", timestamp);
        json.put("data", data);
        //请求图灵api
        String result = PostServer.SendPost(json.toString(), apiUrl);
        return result;
    }

}
