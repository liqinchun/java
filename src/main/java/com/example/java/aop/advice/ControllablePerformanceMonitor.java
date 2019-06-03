package com.example.java.aop.advice;

import com.example.java.aop.proxy.PerformanceMonitor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class ControllablePerformanceMonitor extends DelegatingIntroductionInterceptor implements Monitorable {
    ThreadLocal<Boolean> monitorStatusMap = new ThreadLocal<>();

    @Override
    public void setMonitorActive(boolean active) {
        monitorStatusMap.set(active);
    }

    //拦截方法
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = null;
        if (monitorStatusMap.get() != null && monitorStatusMap.get()){
            PerformanceMonitor.begine(mi.getClass().getName()+"."+mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end(mi.getClass().getName()+"."+mi.getMethod().getName());
        }else {
            obj = super.invoke(mi);
        }
        return obj;
    }
}
