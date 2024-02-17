package ru.aristov.servlets;

import org.reflections.Reflections;
import ru.aristov.servlets.proxies.ProxyApplier;
import ru.aristov.servlets.configuration.Configuration;
import ru.aristov.servlets.configuration.Instance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {

    private final Map<String, Object> instances = new HashMap<>();
    private Map<Class<?>, Object> configInstances = new HashMap<>();
    private List<Method> instanceMethtods = new ArrayList<>();
    private Set<String> instancesInLoading = new HashSet<>();

    public ApplicationContext(String packageName) throws InvocationTargetException, IllegalAccessException {
        scanConfigurationClasses(packageName);
        init();
    }

    public void scanConfigurationClasses (String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configurationClass : configurationClasses) {
            try {
                Object configurationInstance = configurationClass.getDeclaredConstructor().newInstance();
                configInstances.put(configurationClass, configurationInstance);
                for (Method method : configurationClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Instance.class)) {
                        instanceMethtods.add(method);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void init () {
        instanceMethtods.stream()
            .sorted(Comparator.comparingInt((Method method) -> method.getAnnotation(Instance.class).priority()).reversed())
            .forEach(method -> createInstance(method.getName(), method));
    }

    private Object createInstance (String instanceName, Method method) {
        if (instancesInLoading.contains(instanceName)) {
            throw new RuntimeException("Circular dependency: " + instanceName);
        } else {
            instancesInLoading.add(instanceName);

            try {
                Object configInstance = configInstances.get(method.getDeclaringClass());
                Object[] parameters = getParameters(method);
                Object instance = method.invoke(configInstance, parameters);
                instances.put(instanceName, applyProxies(instance));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                instancesInLoading.remove(instanceName);
            }
        }
    }

    private <T> Object applyProxies (Object object) {
        Object result = object;
        for (ProxyApplier applier : getInstances(ProxyApplier.class)) {
            result = applier.apply(result);
        }
        return result;
    }

    private Object[] getParameters (Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(this::getInstance)
                .toArray();
    }

    public <T> T getInstance (Class<T> instanceType) {
        return (T) instances.values().stream()
                .filter(instanceType::isInstance)
                .findFirst()
                .orElseGet(() -> createInstanceByType(instanceType));
    }

    public <T> List<T> getInstances (Class<T> instanceType) {
        return (List<T>) instances.values().stream()
                .filter(instanceType::isInstance)
                .toList();
    }

    public List<Object> getAllInstances () {
        return instances.values().stream().toList();
    }

    private Object createInstanceByType (Class<?> instanceType) {
        for (Method method : instanceMethtods) {
            if (instanceType.isAssignableFrom(method.getReturnType())) {
                return createInstance(method.getName(), method);
            }
        }
        throw new RuntimeException("Not found instance");
    }
}
