package datentypen;


public class Belegung {
    String ebene;
    String regal1;
    String regal2;
    String regal3;
    String regal4;

    public Belegung(String ebene, String regal1, String regal2, String regal3, String regal4) {
        this.ebene = ebene;
        this.regal1 = regal1;
        this.regal2 = regal2;
        this.regal3 = regal3;
        this.regal4 = regal4;
    }

    public String getEbene() {
        return ebene;
    }

    public String getRegal1() {
        return regal1;
    }

    public String getRegal2() {
        return regal2;
    }

    public String getRegal3() {
        return regal3;
    }

    public String getRegal4() {
        return regal4;
    }
}
