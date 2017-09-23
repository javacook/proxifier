public class Hund {

    public Hund() {
    }

    public Hund(String rasse, int gewicht) {
        this.rasse = rasse;
        this.gewicht = gewicht;
    }

    private String rasse;
    private int gewicht;

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
}