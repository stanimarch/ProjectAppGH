package sample;


public class Lager {
    String renr;
    String maxPlatz;
    String platzFrei;

    public Lager(String renr, String maxPlatz, String platzFrei) {
        this.renr = renr;
        this.maxPlatz = maxPlatz;
        this.platzFrei = platzFrei;
    }

    public String getRenr() {
        return renr;
    }

    public String getMaxPlatz() {
        return maxPlatz;
    }

    public String getPlatzFrei() {
        return platzFrei;
    }
}
