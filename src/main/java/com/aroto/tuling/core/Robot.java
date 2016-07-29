package com.aroto.tuling.core;

/**
 * Created by yitao on 2016/7/29.
 */
public class Robot {
    private String name = "小沐沐";
    private SexType sexType = SexType.Human;
    private int age = 1;
    private int limitRequestCount = 5000;
    private String ctime = "2015-01-19 15:02:13";
    private RobotType type = RobotType.Base;
    private String apiKey = "c3beb3ed90e9486801c31d8264d15dff";
    private String secret = "64fc1e8f8a6b9471";

    public Robot() {
    }

    public Robot(String name, SexType sexType, int age, int limitRequestCount, String ctime, RobotType type, String apiKey, String secret) {
        this.name = name;
        this.sexType = sexType;
        this.age = age;
        this.limitRequestCount = limitRequestCount;
        this.ctime = ctime;
        this.type = type;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLimitRequestCount() {
        return limitRequestCount;
    }

    public void setLimitRequestCount(int limitRequestCount) {
        this.limitRequestCount = limitRequestCount;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public RobotType getType() {
        return type;
    }

    public void setType(RobotType type) {
        this.type = type;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
