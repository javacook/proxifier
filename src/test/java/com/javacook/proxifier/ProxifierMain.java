package com.javacook.proxifier;

public class ProxifierMain {

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
