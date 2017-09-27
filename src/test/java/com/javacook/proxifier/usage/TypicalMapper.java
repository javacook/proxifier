package com.javacook.proxifier.usage;

import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;


/**
 * To move the safty check completely to a test class the mapping function has
 * to be converted into the form: <code>fun(sourceBean, targetBean)</code>.
 * You find the result in the class <code>MapperRefactored</code>.
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
