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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hund hund = (Hund) o;
        if (getGewicht() != hund.getGewicht()) return false;
        if (isLieb() != hund.isLieb()) return false;
        return getRasse() != null ? getRasse().equals(hund.rasse) : hund.rasse == null;
    }


    @Override
    public int hashCode() {
        int result = getRasse() != null ? getRasse().hashCode() : 0;
        result = 31 * result + getGewicht();
        result = 31 * result + (isLieb() ? 1 : 0);
        return result;
    }
}