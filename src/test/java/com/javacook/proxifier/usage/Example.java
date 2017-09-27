package com.javacook.proxifier.usage;

import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;
import com.javacook.proxifier.Proxifier;


/**
 * This example shows the working principal of Proxifier. But note,
 * you need not necessarily put the proxification code into your
 * production code as shown here. An example that shows how
 * it can be evacuated into a test class is described in
 * <code>TypicalMapper</code> and <code>MapperRefactored</code>.
 */
public class Example {

    public static void main(String[] args) {
        final Dog dog = new Dog();
        final Hund hund = new Hund("Pudel", 23, true);

        final Dog dogPx = Proxifier.proxyOf(dog);
        final Hund hundPx = Proxifier.proxyOf(hund);

        dogPx.setRace(hundPx.getRasse());
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllSettersInvoked(dogPx);
        Proxifier.assertAllGettersInvoked(hundPx);

        System.out.println(dog);
    }
}
