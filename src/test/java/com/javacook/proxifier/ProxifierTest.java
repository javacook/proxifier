package com.javacook.proxifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class ProxifierTest {

    private Dog dog, dogPx;
    private Hund hund, hundPx;

    @Before
    public void before() {
        Proxifier.enabled = true;
        dog = new Dog();
        hund = new Hund("Pudel", 23, true);
        dogPx = Proxifier.proxyOf(dog);
        hundPx = Proxifier.proxyOf(hund);
    }

    @Test
    public void goodCase() {
        dogPx.setRace(hundPx.getRasse());
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllGettersInvoked(hundPx);
        Proxifier.assertAllSettersInvoked(dogPx);

        Assert.assertEquals("Pudel", dog.getRace());
        Assert.assertEquals(23, dog.getWeight());
        Assert.assertTrue(dog.isNice());
    }

    @Test(expected = IllegalStateException.class)
    public void setterRaceMissing() {
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllSettersInvoked(dogPx);
    }

    @Test
    public void setterRaceMissingButExceptProp() {
        hund.setLieb(false);
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllSettersInvoked(dogPx, "race");
        Assert.assertNull(dog.getRace());
        Assert.assertEquals(23, dog.getWeight());
        Assert.assertFalse(dog.isNice());
    }

    @Test
    public void setterRaceMissingButExceptSetter() {
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllSettersInvoked(dogPx, "setRace");
    }

    @Test(expected = IllegalStateException.class)
    public void getterRasseMissing() {
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllGettersInvoked(hundPx);
    }

    @Test
    public void getterRasseMissingButExceptProp() {
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllGettersInvoked(hundPx, "rasse");
    }

    @Test
    public void getterRasseMissingButExceptGetter() {
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllGettersInvoked(hundPx, "getRasse");
    }

    @Test
    public void getterLiebMissingButExceptProp() {
        dogPx.setRace(hundPx.getRasse());
        dogPx.setWeight(hundPx.getGewicht());

        Proxifier.assertAllGettersInvoked(hundPx, "lieb");
    }

    @Test
    public void getterLiebMissingButExceptIsser() {
        dogPx.setRace(hundPx.getRasse());
        dogPx.setWeight(hundPx.getGewicht());

        Proxifier.assertAllGettersInvoked(hundPx, "isLieb");
    }

    @Test
    public void gettersNotInvoked1() {
        final Set<String> actual = Proxifier.gettersNotInvoked(hundPx);
        Assert.assertEquals(new HashSet(){{add("isLieb");add("getRasse");add("getGewicht");}}, actual);
    }

    @Test
    public void gettersNotInvoked2() {
        final Set<String> actual = Proxifier.gettersNotInvoked(hundPx, "lieb", "rasse");
        Assert.assertEquals(new HashSet(){{add("getGewicht");}}, actual);
    }

    @Test
    public void gettersNotInvoked3() {
        final Set<String> actual = Proxifier.gettersNotInvoked(hundPx, "isLieb", "getGewicht", "getRasse");
        Assert.assertEquals(new HashSet(), actual);
    }


    @Test
    public void settersNotInvoked1() {
        final Set<String> actual = Proxifier.settersNotInvoked(dogPx);
        Assert.assertEquals(new HashSet(){{add("setNice");add("setRace");add("setWeight");}}, actual);
    }

    @Test
    public void settersNotInvoked2() {
        final Set<String> actual = Proxifier.settersNotInvoked(dogPx, "nice", "race");
        Assert.assertEquals(new HashSet(){{add("setWeight");}}, actual);
    }

    @Test
    public void settersNotInvoked3() {
        final Set<String> actual = Proxifier.settersNotInvoked(dogPx, "setNice", "setWeight", "setRace");
        Assert.assertEquals(new HashSet(), actual);
    }

    @Test
    public void setterRaceMissingButDisabeled() {
        Proxifier.enabled = false;
        dogPx.setWeight(hundPx.getGewicht());
        dogPx.setNice(hundPx.isLieb());

        Proxifier.assertAllSettersInvoked(dogPx);
        Proxifier.assertAllGettersInvoked(hundPx);

        Assert.assertNull(dog.getRace());
        Assert.assertEquals(23, dog.getWeight());
        Assert.assertTrue(dog.isNice());
    }

}