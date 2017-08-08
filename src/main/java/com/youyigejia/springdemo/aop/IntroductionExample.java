package com.youyigejia.springdemo.aop;

import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionExample {
    public static void main(String[] args) {
        TargetBean target = new TargetBean();
        target.setName("name");
        IntroductionAdvisor introductionAdvisor = new IsModifiedAdvisor();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setOptimize(true);
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(introductionAdvisor);
        TargetBean proxy = (TargetBean) proxyFactory.getProxy();
        IsModified isModified= (IsModified) proxy;
        System.out.println(isModified.isModified());
        proxy.setName("大海");
        System.out.println(isModified.isModified());
        proxy.setName("大王");
        System.out.println(isModified.isModified());

    }
}
