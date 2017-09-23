package com.javacook.proxifier;

public class ProxifierMain {

    public static void main(String[] args) {
        final Dog dog = new Dog();
        final Hund hund = new Hund("Pudel", 23, true);
        final Dog dogProxy = Proxifier.proxyOf(dog);
        final Hund hundProxy = Proxifier.proxyOf(hund);
        dogProxy.setRace(hundProxy.getRasse());
        dogProxy.setWeight(hundProxy.getGewicht());
        dogProxy.setNice(hundProxy.isLieb());
        Proxifier.assertAllSettersInvoked(dogProxy, "setNice");
        Proxifier.assertAllGettersInvoked(hundProxy, "isLieb");

        System.out.println(dog);
    }
}
