package com.javacook.proxifier.usage;

import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;


/**
 * This is the signature of a typical mapper (maybe the methods are not
 * necessarily static).
 *
 * To move the safe1ty check completely to a test class the mapping function
 * <code>mapHundToDog</code> has to be converted into the form:
 * <pre>
 * <code>fun(sourceBean, targetBean)</code>.
 * </pre>
 * You find an example of the conversion in the class <code>MapperRefactored</code>.
 * @see MapperRefactored
 */
public class TypicalMapper {

    /**
     * Maps a bean of class <code>Hund</code> to a bean of class <code>Dog</code>
     * @param hund source object
     * @return {@link Dog}
     */
    public static Dog mapHundToDog(final Hund hund) {
        final Dog dog = new Dog();
        dog.setRace(hund.getRasse());
        dog.setWeight(hund.getGewicht());
        dog.setNice(hund.isLieb());
        return dog;
    }

}
