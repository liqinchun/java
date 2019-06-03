package com.example.java.aop.proxy;

//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.cglib.proxy.Callback;
//import org.aopalliance.intercept.MethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Callback;
//import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //创建子类的实例
        return enhancer.create();
    }


    @Override
    public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        PerformanceMonitor.begine(target.getClass().getName()+"."+method.getName());

        Object result = methodProxy.invokeSuper(target,objects);
        PerformanceMonitor.end(target.getClass().getName()+"."+method.getName());
        return result;
    }
}
