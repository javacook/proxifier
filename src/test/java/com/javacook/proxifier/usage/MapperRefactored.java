package com.javacook.proxifier.usage;

import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;

/**
 * If the mapping function is of the form <code>fun(sourceBean, targetBean)</code>,
 * i.e. if the target object is also passed as an parameter, the safety check can
 * be moved completely into an external test.
 * @see MapperTest
 */
public class MapperRefactored {

    /**
     * The mapper method has to be transformed a little bit so that
     * the source and target bean are arguments
     * @param hund source object
     * @param dog target object
     */
    static void mapHundToDog(final Hund hund, final Dog dog) {
        dog.setRace(hund.getRasse());
        dog.setWeight(hund.getGewicht());
        dog.setNice(hund.isLieb());
    }

    /**
     * The original typical mapper method can also be maintained so
     * that caller of this method need not to be changed
     * @param hund source object
     * @return target object
     * @see TypicalMapper
     */
    public static Dog mapHundToDog(final Hund hund) {
        final Dog dog = new Dog();
        mapHundToDog(hund, dog);
        return dog;
    }

}
