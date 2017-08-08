package com.youyigejia.springdemo.aop;

import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionExample {
    public static void main(String[] args) {
        TargetBean target = new TargetBean();
        target.setName("name");
        IntroductionAdvisor introductionAdvisor = new IsModifiedAdvisor();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(introductionAdvisor);
        proxyFactory.setOptimize(true);
        TargetBean targetBean = (TargetBean) proxyFactory.getProxy();
        IsModified isModified= (IsModified) target;

    }
}
