package com.javacook.proxifier.usage;

import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;


/**
 * If the mapping function is of form <code>fun(sourceBean, targetBean)</code>, i.e.
 * if the target object is passed as an parameter, the safety check can be completely
 * moved into an external test.
 * @see MapperTest
 */
public class Mapper {

    /**
     * Maps a bean of class <code>Hund</code> to a bean of class <code>Dog</code>
     * @param hund source object
     * @param dog target object
     */
    public static void mapHundToDog(final Hund hund, final Dog dog) {
        dog.setRace(hund.getRasse());
        dog.setWeight(hund.getGewicht());
        dog.setNice(hund.isLieb());
    }

}
