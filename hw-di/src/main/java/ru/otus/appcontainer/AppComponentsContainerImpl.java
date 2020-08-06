package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method method : methods) {
            Object bean = createBean(method, configClass);
            appComponents.add(bean);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), bean);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object createBean(Method method, Class<?> configClass) {
        Object bean = null;
        try {
            bean = method.invoke(configClass.getDeclaredConstructor().newInstance(),
                    Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toArray());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return bean;
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(componentClass::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Нет такого элемента!!"));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
