package ru.aristov.servlets;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LoggingProxyApplierImpl implements ProxyApplier {
    @Override
    public Object apply(Object object) {
        boolean isProxyApplier = Arrays.stream(object.getClass().getInterfaces())
                .anyMatch(it -> it.isAnnotationPresent(ControllerAnnotation.class));
        if (isProxyApplier) {
            return Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if (method.isAnnotationPresent(Logged.class)) {
                        System.out.println("Начало метода");
                        Object result = method.invoke(object, args);
                        System.out.println("Конец метода");
                        return result;
                    } else {
                        return method.invoke(object, args);
                    }
                }
            );
        } else {
            return object;
        }
    }
}
