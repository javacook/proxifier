package com.javacook.enumcheck.usage;

import com.javacook.enumcheck.EnumCheck;
import com.javacook.enumcheck.testdata.ColorEnum;
import com.javacook.enumcheck.testdata.FarbeEnum;

import java.util.Set;


public class Example {

    public static void main(String[] args) throws Exception {
        Set<FarbeEnum> farbeEnums = EnumCheck.sourceValuesNotMapped(TypicalMapper::mapFarbeToColor, FarbeEnum.class);
        System.out.println(farbeEnums);
        Set<ColorEnum> colorEnums = EnumCheck.destValuesNotMapped(TypicalMapper::mapFarbeToColor, FarbeEnum.class, ColorEnum.class);
        System.out.println(colorEnums);
    }

}
