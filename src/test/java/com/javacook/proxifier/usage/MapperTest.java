package com.javacook.proxifier.usage;

import com.javacook.proxifier.Proxifier;
import com.javacook.proxifier.testdata.Dog;
import com.javacook.proxifier.testdata.Hund;
import org.junit.Test;


public class MapperTest {

    @Test
    public void testMapper() {

        // Create test data:
        final Dog dog = new Dog();
        final Hund hund = new Hund("Pudel", 23, true);

        // Replace the test objects with proxies:
        final Dog dogPx = Proxifier.proxyOf(dog);
        final Hund hundPx = Proxifier.proxyOf(hund);

        // Call the mapper:
        Mapper.mapHundToDog(hundPx, dogPx);

        // Safety check:
        Proxifier.assertAllGettersInvoked(hundPx);
        Proxifier.assertAllSettersInvoked(dogPx);
    }

}
