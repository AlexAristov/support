package ru.aristov.servlets.routing;

import ru.aristov.servlets.ApplicationContext;
import ru.aristov.servlets.controllers.Controller;
import ru.aristov.servlets.controllers.HttpMethod;
import ru.aristov.servlets.controllers.RequestMapping;
import ru.aristov.servlets.controllers.supportController.SupportController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonMappingProviderImpl implements MappingProvider {
    private final ApplicationContext context;
    private final Map<Mapping, Handler> mappingToHandler = new HashMap<>();

    public CommonMappingProviderImpl(ApplicationContext context) {
        this.context = context;
        init();
    }

    private void init() {
        List<Object> controllers = context.getAllInstances().stream()
                .filter(it -> Arrays.stream(it.getClass().getInterfaces()).anyMatch(i -> i.isAnnotationPresent(Controller.class)))
                .toList();

        for (Object controller : controllers) {
            Class<?> interfaceOfController = getInterface(controller);

            if (interfaceOfController != null) {
                Arrays.stream(interfaceOfController.getMethods())
                        .filter(it ->it.isAnnotationPresent(RequestMapping.class))
                        .forEach(method -> addMapping(controller, method));
            }
        }
    }

    private Class<?> getInterface(Object controller) {
        Class<?>[] interfaces = controller.getClass().getInterfaces();
        Class<?> interfaceOfController = null;
        for (Class<?> anInterface : interfaces) {
            if (anInterface.equals(SupportController.class)) {
                interfaceOfController = anInterface;
            }
        }
        return interfaceOfController;
    }

    @Override
    public Handler getMapping(HttpServletRequest request) {
        return mappingToHandler.get(new Mapping(request.getPathInfo().toString(), HttpMethod.valueOf(request.getMethod())));
    }

    private void addMapping (Object controller, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        mappingToHandler.put(new Mapping(annotation.path(), annotation.method()), new Handler(controller, method));
    }

    public record Mapping (String path, HttpMethod method) {

    }

    public record Handler (Object target, Method method) {

    }
}
