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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dog dog = (Dog) o;

        if (getWeight() != dog.getWeight()) return false;
        if (isNice() != dog.isNice()) return false;
        return getRace() != null ? getRace().equals(dog.getRace()) : dog.getRace() == null;
    }


    @Override
    public int hashCode() {
        int result = getRace() != null ? getRace().hashCode() : 0;
        result = 31 * result + getWeight();
        result = 31 * result + (isNice() ? 1 : 0);
        return result;
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