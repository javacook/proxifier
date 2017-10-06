package com.javacook.enumcheck.usage;

import com.javacook.enumcheck.testdata.ColorEnum;
import com.javacook.enumcheck.testdata.FarbeEnum;

import java.util.Set;


public class TypicalMapper {

    public static ColorEnum mapFarbeToColor(FarbeEnum farbeEnum) {
        switch (farbeEnum) {
            case ROT: return ColorEnum.RED;
            case GELB: return ColorEnum.YELLOW;
            case ORANGE: return ColorEnum.ORANGE;
            case GRUEN: return ColorEnum.GREEN;
            default: throw new IllegalArgumentException("Unknown enum :" + farbeEnum);
        }
    }

}
