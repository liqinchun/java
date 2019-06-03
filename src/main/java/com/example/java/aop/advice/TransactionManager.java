package com.example.java.aop.advice;


import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 模拟异常增强事务处理
 */
public class TransactionManager implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target,
                              Exception ex) {
        System.out.println("method:" + method.getName());
        System.out.println("抛出异常:"+ex.getMessage());
        System.out.println("回滚事务!");
    }
}
