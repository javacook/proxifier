package com.javacook.proxifier;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;


public class Proxifier {

    public static boolean enabled = true;

    private final static Map<Object, Set<String>> setters = new WeakHashMap<>();
    private final static Map<Object, Set<String>> getters = new WeakHashMap<>();

    public static <T> T proxyOf(final T object)  {
        Objects.requireNonNull(object, "Argument object is null");
        if (!enabled) {
            return object;
        }
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(object.getClass());
        factory.setFilter(method ->
                Modifier.isPublic(method.getModifiers()) &&
                !Modifier.isStatic(method.getModifiers()) &&
                (
                   (BeanNameUtils.isSetterOrGetterOrIsserName(method.getName()) && !"getClass".equals(method.getName()))
                      || BeanNameUtils.isEqualsOrHashCode(method.getName()
                   )
                )
        );

        MethodHandler methodHandler = (self, thisMethod, proceed, args) -> {
            final String methodName = thisMethod.getName();

            if ("equals".equals(methodName) && args.length == 1) {
                return (Boolean)(self == args[0]);
            }
            else if ("hashCode".equals(methodName) && args.length == 0) {
                return (Integer)System.identityHashCode(self);
            }
            else {
                setters.get(self).remove(thisMethod.getName());
                getters.get(self).remove(thisMethod.getName());
                return thisMethod.invoke(object, args);
            }
        };

        try {
            T wrapper = (T) factory.create(new Class<?>[0], new Object[0], methodHandler);

            final Set<String> setters = Arrays.stream(object.getClass().getMethods())
                    .map(method -> method.getName())
                    .filter(methodName -> BeanNameUtils.isSetterName(methodName))
                    .collect(Collectors.toSet());

            final Set<String> getters = Arrays.stream(object.getClass().getMethods())
                    .map(method -> method.getName())
                    .filter(methodName ->
                            BeanNameUtils.isGetterOrIsserName(methodName) && !"getClass".equals(methodName))
                    .collect(Collectors.toSet());

            Proxifier.setters.put(wrapper, setters);
            Proxifier.getters.put(wrapper, getters);
            return wrapper;
        }
        catch (NoSuchMethodException e ) {
            e.getMessage();
            throw new IllegalArgumentException("The class to be proxified needs a default constructor: " + extractClassName(e));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Set<String> settersNotInvoked(Object wrapper, String... excludeProperties) {
        if (!enabled) {
            return Collections.EMPTY_SET;
        }
        final Set<String> excludePropSet = Arrays.stream(excludeProperties)
                .map(prop -> BeanNameUtils.prependSet(prop))
                .collect(Collectors.toSet());
        return Proxifier.setters.get(wrapper).stream()
                .filter(setter -> !excludePropSet.contains(setter))
                .collect(Collectors.toSet());
    }


    public static Set<String> gettersNotInvoked(Object wrapper, String... excludeProperties) {
        if (!enabled) {
            return Collections.EMPTY_SET;
        }
        final Set<String> excludePropSet = Arrays.stream(excludeProperties)
                .flatMap(prop -> Arrays.asList(BeanNameUtils.prependGet(prop), BeanNameUtils.prependIs(prop)).stream())
                .collect(Collectors.toSet());
        return Proxifier.getters.get(wrapper).stream()
                .filter(getter -> !excludePropSet.contains(getter))
                .collect(Collectors.toSet());
    }


    public static void assertAllSettersInvoked(Object wrapper, String... excludeProperties) {
        final Set<String> setters = settersNotInvoked(wrapper, excludeProperties);
        if (!setters.isEmpty()) {
            throw new IllegalStateException("Setters not invoked: " + setters);
        }
    }

    public static void assertAllGettersInvoked(Object wrapper, String... excludeProperties) {
        final Set<String> getters = gettersNotInvoked(wrapper, excludeProperties);
        if (!getters.isEmpty()) {
            throw new IllegalStateException("Getters not invoked: " + getters);
        }
    }


    private static String extractClassName(NoSuchMethodException e) {
        final String message = e.getMessage();
        if (message == null) return "No further class details";
        final int indexOfUnderscore = message.indexOf("_");
        return (indexOfUnderscore < 0)? message : message.substring(0, indexOfUnderscore);
    }

}