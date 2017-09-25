package com.javacook.proxifier.testdata;

public class Hund {

    public Hund() {
    }

    public Hund(String rasse, int gewicht, boolean lieb) {
        this.rasse = rasse;
        this.gewicht = gewicht;
        this.lieb = lieb;
    }


    private String rasse;
    private int gewicht;
    private boolean lieb;

    public void bark() {
        System.out.println("Woof!");
    }

    public String getRasse() {
        return rasse;
    }

    public void setRasse(String rasse) {
        this.rasse = rasse;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public boolean isLieb() {
        return lieb;
    }

    public void setLieb(boolean lieb) {
        this.lieb = lieb;
    }
}