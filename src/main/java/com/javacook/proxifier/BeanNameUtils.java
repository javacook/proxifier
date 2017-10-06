package com.javacook.proxifier;

import java.util.Objects;


public class BeanNameUtils {

    public static boolean isSetterName(String name) {
        Objects.requireNonNull(name, "Argument name is null");
        return name.length() >= 4 && name.startsWith("set") && Character.isUpperCase(name.charAt(3));
    }

    public static boolean isGetterName(String name) {
        Objects.requireNonNull(name, "Argument name is null");
        return (name.length() >= 4 && name.startsWith("get") && Character.isUpperCase(name.charAt(3)));
    }

    public static boolean isIsserName(String name) {
        Objects.requireNonNull(name, "Argument name is null");
        return (name.length() >= 3 && name.startsWith("is") && Character.isUpperCase(name.charAt(2)));
    }

    public static boolean isGetterOrIsserName(String name) {
        return isGetterName(name) || isIsserName(name);
    }

    public static boolean isSetterOrGetterOrIsserName(String name) {
        return isSetterName(name) || isGetterOrIsserName(name);
    }

    public static boolean isEqualsOrHashCode(String name) {
        Objects.requireNonNull(name, "Argument name is null");
        return "equals".equals(name) || "hashCode".equals(name);
    }

    public static String prependSet(String setterOrProp) {
        return isSetterName(setterOrProp)? setterOrProp : "set" + toUpperFirstChar(setterOrProp);
    }

    public static String prependGet(String getterOrProp) {
        return isGetterOrIsserName(getterOrProp)? getterOrProp : "get" + toUpperFirstChar(getterOrProp);
    }

    public static String prependIs(String getterOrProp) {
        return isGetterOrIsserName(getterOrProp)? getterOrProp : "is" + toUpperFirstChar(getterOrProp);
    }

    public static String toUpperFirstChar(String str) {
        if ("".equals(Objects.requireNonNull(str))) {
            return "";
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
