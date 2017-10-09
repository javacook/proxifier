package com.javacook.proxifier;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;


/**
 * This class can be used to check which setters and getters of a (bean) object
 * have been called, in particular to validate bean mappers. For the usages you
 * can find some examples in the test folder.
 */
public class Proxifier {

    public static boolean enabled = true;

    private final static Map<Object, Set<String>> setters = new WeakHashMap<>();
    private final static Map<Object, Set<String>> getters = new WeakHashMap<>();


    /**
     * Produces a proxy of <code>object</code> that is able to count the setter
     * and getter calls.
     * @param object the original (bean) object to proxify
     * @param <T> type of the original object
     * @return a proxification of <code>object</code>
     */
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

            // equals and hashCode have to be overwritten to a simple form since
            // the implementation of the original objects may have used getters and
            // in their implementation what leads to an infinite loop when removing
            // their getters resp. setter from the weak hash map (see the else branch)
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
            T proxy = (T) factory.create(new Class<?>[0], new Object[0], methodHandler);

            final Set<String> setters = Arrays.stream(object.getClass().getMethods())
                    .map(method -> method.getName())
                    .filter(methodName -> BeanNameUtils.isSetterName(methodName))
                    .collect(Collectors.toSet());

            final Set<String> getters = Arrays.stream(object.getClass().getMethods())
                    .map(method -> method.getName())
                    .filter(methodName ->
                            BeanNameUtils.isGetterOrIsserName(methodName) && !"getClass".equals(methodName))
                    .collect(Collectors.toSet());

            Proxifier.setters.put(proxy, setters);
            Proxifier.getters.put(proxy, getters);
            return proxy;
        }
        catch (NoSuchMethodException e ) {
            e.getMessage();
            throw new IllegalArgumentException("The class to be proxified needs a default constructor: " + extractClassName(e));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the set of setters which have not been invoked with respect to a set of
     * properties (or setter names) that should be excluded
     * @param proxy the proxified object yielded from <code>proxyOf</code>
     * @param excludeProperties names of properties or setters not to check
     * @return set of setter names
     */
    public static Set<String> settersNotInvoked(Object proxy, String... excludeProperties) {
        if (!enabled) {
            return Collections.EMPTY_SET;
        }
        final Set<String> excludePropSet = Arrays.stream(excludeProperties)
                .map(prop -> BeanNameUtils.prependSet(prop))
                .collect(Collectors.toSet());
        return Proxifier.setters.get(proxy).stream()
                .filter(setter -> !excludePropSet.contains(setter))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the set of getters which have not been invoked with respect to a set of
     * properties (or getter names) that should be excluded
     * @param proxy the proxified object yielded from <code>proxyOf</code>
     * @param excludeProperties names of properties or getters not to check
     * @return set of getter names
     */
    public static Set<String> gettersNotInvoked(Object proxy, String... excludeProperties) {
        if (!enabled) {
            return Collections.EMPTY_SET;
        }
        final Set<String> excludePropSet = Arrays.stream(excludeProperties)
                .flatMap(prop -> Arrays.asList(BeanNameUtils.prependGet(prop), BeanNameUtils.prependIs(prop)).stream())
                .collect(Collectors.toSet());
        return Proxifier.getters.get(proxy).stream()
                .filter(getter -> !excludePropSet.contains(getter))
                .collect(Collectors.toSet());
    }

    /**
     * Used in tests to check whether all setters have been invoked
     * @param proxy the proxified object yielded from <code>proxyOf</code>
     * @param excludeProperties names of properties or setters not to check
     * @throws IllegalStateException if not all setters were invoked 
     */
    public static void assertAllSettersInvoked(Object proxy, String... excludeProperties) {
        final Set<String> setters = settersNotInvoked(proxy, excludeProperties);
        if (!setters.isEmpty()) {
            throw new IllegalStateException("Setters not invoked: " + setters);
        }
    }

    /**
     * Used in tests to check whether all getters have been invoked
     * @param proxy the proxified object yielded from <code>proxyOf</code>
     * @param excludeProperties names of properties or getters not to check
     * @throws IllegalStateException if not all getters were invoked
     */
    public static void assertAllGettersInvoked(Object proxy, String... excludeProperties) {
        final Set<String> getters = gettersNotInvoked(proxy, excludeProperties);
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