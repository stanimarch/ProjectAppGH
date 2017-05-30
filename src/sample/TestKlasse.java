package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestKlasse {
    public static void main(String[] args) {
        Main main = new Main();
        ObservableList<Product> products = FXCollections.observableArrayList();
        products = main.getProduct();
        for (Product product : products){
            System.out.println(product.id + " " + product.farbe + " " + product.menge);
        }
    }
}
