package ru.aristov;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class SubscriberAnnotationBeenPostProcessor implements BeanPostProcessor {
    private final Map<String, Object> beansToRun = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscriber.class)) {
                beansToRun.put(beanName, bean);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object object = beansToRun.get(beanName);
        if (object != null) {
            Thread childTread = new Thread((Runnable) object);
            childTread.start();
        }

        return bean;
    }
}
