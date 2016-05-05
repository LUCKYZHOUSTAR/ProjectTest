package com.Reflect;

public class User implements parent{

    private String name;
    private String pwd;

    public String number;
    private void run() {
        System.out.println("开始奔跑");
    }

    
    private String fly(String startTime,String endTime){
        System.out.println("开始奔跑");
        System.out.println(startTime+endTime);
        return "跑起来了";
    }
    public void eat(String food) {
        System.out.println("开始吃" + food);
    }

    public static String fly(String fly) {
        return fly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    
    
}


interface parent{
    
}
