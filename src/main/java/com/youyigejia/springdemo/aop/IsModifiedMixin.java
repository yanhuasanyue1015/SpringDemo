package com.youyigejia.springdemo.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class IsModifiedMixin extends DelegatingIntroductionInterceptor implements IsModified {
    private boolean isModified = false;
    private Map methodCache = new HashMap();


    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(isModified)) {
            if (mi.getMethod().getName().startsWith("set") && mi.getArguments().length == 1) {
                Method getter = getGetter(mi.getMethod());
                if (getter != null) {
                    Object newValue = mi.getArguments()[0];
                    Object oldValue = getter.invoke(mi.getThis());
                    if (newValue == null && oldValue == null) {
                        isModified = false;
                    } else if (newValue == null && oldValue != null) {
                        isModified = true;
                    } else if (newValue != null && oldValue == null) {
                        isModified = true;
                    } else {
                        isModified = (!newValue.equals(oldValue));
                    }
                }
            }
        }
        return super.invoke(mi);
    }

    private Method getGetter(Method setter) {
        Method gettter = (Method) methodCache.get(setter);
        if (gettter != null) {
            return gettter;
        }
        String getterName = setter.getName().replaceFirst("set", "get");
        try {
            gettter = setter.getDeclaringClass().getMethod(getterName);
            synchronized (methodCache) {
                methodCache.put(setter, gettter);
            }
            return gettter;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
