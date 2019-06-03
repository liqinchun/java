package com.example.java.aop.proxy;

public class MethodPerformace {

    private long begin;
    private long end;
    private String serviceMethod;
    public MethodPerformace(String method) {
        this.serviceMethod = method;
        this.begin = System.currentTimeMillis();
    }

    public void printPerformance(){
        end = System.currentTimeMillis();
        long elapse = end -begin;
        System.out.println(serviceMethod + "花费了"+elapse+"毫秒");
    }
}
