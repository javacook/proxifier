package com.javacook.proxifier.testdata;

public class Dog {

    private String race;
    private int weight;
    private boolean nice;

    public void bark() {
        System.out.println("Woof!");
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }


    public int getWeight() {
        return weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isNice() {
        return nice;
    }

    public void setNice(boolean lovely) {
        this.nice = lovely;
    }


    @Override
    public String toString() {
        return "Dog{" +
                "race='" + race + '\'' +
                ", weight=" + weight +
                ", nice=" + nice +
                '}';
    }
}