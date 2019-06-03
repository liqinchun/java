package com.example.java.aop.proxy;

public class PerformanceMonitor {

    private static ThreadLocal<MethodPerformace> performaceThreadLocal =
            new ThreadLocal<>();

    public static void begine(String method){

        System.out.println("begine monitor ...");
        MethodPerformace mp = new MethodPerformace(method);
    }

    public static void end(String method){

        System.out.println("end monitor ...");
        MethodPerformace mp = new MethodPerformace(method);
    }
}
