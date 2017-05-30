package datentypen;


public class Product {
    String id;
    String farbe;
    String menge;
    Main main;

    public Product(String id, String farbe, String menge) {
        this.id = id;
        this.farbe = farbe;
        this.menge = menge;
    }

    public String getId() {
        return id;
    }

    public String getFarbe() {
        return farbe;
    }

    public String getMenge() {
        return menge;
    }
}
