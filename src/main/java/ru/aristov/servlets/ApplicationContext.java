package ru.aristov.servlets;

import org.reflections.Reflections;
import ru.aristov.servlets.configuration.Configuration;
import ru.aristov.servlets.configuration.Instance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public ApplicationContext() throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("ru.aristov.servlets.configuration");
        List<?> configurations = reflections.getTypesAnnotatedWith(Configuration.class)
            .stream()
            .map(type -> {
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        for (Object configuration : configurations) {
            List<Method> methods = Arrays.stream(configuration.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Instance.class))
                    .toList();

            for (Method method : methods) {
                instances.put(method.getReturnType(), method.invoke(configuration));
            }
        }
    }

    public <T> T getInstance(Class<T> type) {
        return (T) Optional.ofNullable(this.instances.get(type)).orElseThrow();
    }

}
