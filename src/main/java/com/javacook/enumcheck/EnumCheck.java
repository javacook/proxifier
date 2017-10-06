package com.javacook.enumcheck;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;


public class EnumCheck {

    public static <S extends Enum<S>, D extends Enum<D>>
    Set<S> sourceValuesNotMapped(Function<S, D> mapper, Class<S> enumSource) {

        Set<S> valueSetSource = new HashSet<>(Arrays.asList(values(enumSource)));
        Set<S> result = new HashSet<>();
        for (S s : valueSetSource) {
            try {
                mapper.apply(s);
            }
            catch (Exception e) {
                result.add(s);
            }
        }
        return result;
    }


    public static <S extends Enum<S>, D extends Enum<D>>
    Set<D> destValuesNotMapped(Function<S, D> mapper, Class<S> enumSource, Class<D> enumDest) {

        Set<S> valueSetSource = new HashSet<>(Arrays.asList(values(enumSource)));
        Set<D> valueSetDest = new HashSet<>(Arrays.asList(values(enumDest)));
        Set<D> mappedValues = new HashSet<>();
        for (S s : valueSetSource) {
            try {
                mappedValues.add((D) mapper.apply(s));
            }
            catch (Exception e) {}
        }
        valueSetDest.removeAll(mappedValues);
        return valueSetDest;
    }


    private static <S extends Enum<S>> S[] values(Class<S> enumSource) {
        try {
            final Method methodSource = enumSource.getMethod("values");
            return (S[]) methodSource.invoke(enumSource);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
