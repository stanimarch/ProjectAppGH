import datentypen.Belegung;
import datentypen.Lager;
import datentypen.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.*;

public class DBTabellenController extends Controller {

    // ну я думаю, что эту "table" можно удалить,
    // так как толку мало от этой таблицы
    /*@FXML
    TableView table;*/

    @FXML
    Pane pane;

    @FXML
    Text text;

    // а вот эти таблцы я бы оставил
    TableView<Product> tableProdukt = new TableView<>();
    TableView<Lager> tableLager = new TableView<>();
    TableView<Belegung> tableBelegung = new TableView<>();

    //в теории, если эти одну из этих 3 переменных изменить, то должно и таблица в layout изменится
    ObservableList<Product> productListe;
    ObservableList<Lager> lagerListe;
    ObservableList<Belegung> belegungListe;

    @FXML
    @Override // метода, что бы показать главное окно
    public void showTabellenWindow() {
        System.out.println("public void showTabellenWindow()");
        aktualisieren();
        main.changeScene(3);
    }

    @FXML
    public void aktualisieren() {
        System.out.println("public void aktualisieren()");
        productListe = getProductListe();
        tableProdukt.setItems(productListe);
        lagerListe = getLagerListe();
        tableLager.setItems(lagerListe);
    }

    @FXML
    public void zeigTabelleProdukt() {
        System.out.println("public void zeigTabelleProdukt()");

        pane.getChildren().clear();
        aktualisieren();
        pane.getChildren().addAll(tableProdukt);
        pane.setMaxSize(500, 300);
        text.setText("Tabelle PRODUKT");
    }

    @FXML
    public void zeigTabelleLager() {
        System.out.println("public void zeigTabelleLager()");

        pane.getChildren().clear();
        aktualisieren();
        pane.getChildren().addAll(tableLager);
        pane.setMaxSize(500, 300);
        text.setText("Tabelle LAGER");
    }

    @FXML
    public void zeigTabelleBelegung() {
        pane.getChildren().clear();
        text.setText("");
        System.out.println("public void zeigTabelleBelegung()");
    }

    //что бы все таблицы инициализировать. То есть, что бы появились заголовки(Spaltenname) в таблицах
    void tabellenAnlegen() {

        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> farbeColumn = new TableColumn<>("FARBE");
        farbeColumn.setMinWidth(200);
        farbeColumn.setCellValueFactory(new PropertyValueFactory<>("farbe"));

        TableColumn<Product, String> mengeColumn = new TableColumn<>("MENGE");
        mengeColumn.setMinWidth(150);
        mengeColumn.setCellValueFactory(new PropertyValueFactory<>("menge"));

        tableProdukt.setItems(getProductListe());
        tableProdukt.getColumns().addAll(idColumn, farbeColumn, mengeColumn);
        tableProdukt.setMaxSize(500, 300);


        TableColumn<Lager, String> renrColumn = new TableColumn<>("REGALNUMMER");
        renrColumn.setMinWidth(150);
        renrColumn.setCellValueFactory(new PropertyValueFactory<>("renr"));

        TableColumn<Lager, String> maxPlatzColumn = new TableColumn<>("Kapazität");
        maxPlatzColumn.setMinWidth(200);
        maxPlatzColumn.setCellValueFactory(new PropertyValueFactory<>("maxPlatz"));

        TableColumn<Lager, String> platzFreiColumn = new TableColumn<>("NOCH FREI");
        platzFreiColumn.setMinWidth(150);
        platzFreiColumn.setCellValueFactory(new PropertyValueFactory<>("platzFrei"));

        tableLager.setItems(getLagerListe());
        tableLager.getColumns().addAll(renrColumn, maxPlatzColumn, platzFreiColumn);
        tableLager.setMaxSize(500, 300);

        /*TableColumn<Belegung, String> ebeneColumn = new TableColumn<>("FACHNUMMER");
        ebeneColumn.setMinWidth(150);
        ebeneColumn.setCellValueFactory(new PropertyValueFactory<>("ebene"));

        TableColumn<Belegung, String> regal1Column = new TableColumn<>("REGAL 1");
        regal1Column.setMinWidth(200);
        regal1Column.setCellValueFactory(new PropertyValueFactory<>("regal1"));

        TableColumn<Belegung, String> regal2Column = new TableColumn<>("REGAL 2");
        regal2Column.setMinWidth(150);
        regal2Column.setCellValueFactory(new PropertyValueFactory<>("regal2"));

        TableColumn<Belegung, String> regal3Column = new TableColumn<>("REGAL 3");
        regal3Column.setMinWidth(150);
        regal3Column.setCellValueFactory(new PropertyValueFactory<>("regal3"));

        TableColumn<Belegung, String> regal4Column = new TableColumn<>("REGAL 4");
        regal4Column.setMinWidth(150);
        regal4Column.setCellValueFactory(new PropertyValueFactory<>("regal4"));

        tableBelegung.setItems(getBelegungListe());
        tableBelegung.getColumns().addAll(ebeneColumn, regal1Column, regal2Column, regal3Column, regal4Column);*/
    }

    ObservableList<Product> getProductListe() {
        if (productListe == null)
            productListe = FXCollections.observableArrayList();
        else
            productListe.clear();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dionysos.informatik.hs-augsburg.de/db01";
            Connection conn = null;
            //conn = DriverManager.getConnection(url, "db01", "MenPfN9wzcZA");
            conn = DriverManager.getConnection(url, "andr_user", "androiduser");
            String sql = "select * from produkt order by prnr";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                //
                //System.out.print(rs.getInt(1) + " ");
                //System.out.print(rs.getString(2) + " ");
                //System.out.println(rs.getInt(3));
                //System.out.print(rs.getString("prnr") + "\t");
                //System.out.print(rs.getString("name") + "\t");
                //System.out.println(rs.getString("menge") + "\t");
                productListe.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return productListe;
    }

    ObservableList<Lager> getLagerListe() {
        if (lagerListe == null)
            lagerListe = FXCollections.observableArrayList();
        else
            lagerListe.clear();

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dionysos.informatik.hs-augsburg.de/db01";
            Connection conn = null;
            //conn = DriverManager.getConnection(url, "db01", "MenPfN9wzcZA");
            conn = DriverManager.getConnection(url, "andr_user", "androiduser");
            String sql = "select * from lager order by renr";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                //
                //System.out.print(rs.getInt(1) + " ");
                //System.out.print(rs.getString(2) + " ");
                //System.out.println(rs.getInt(3));
                //System.out.print(rs.getString("prnr") + "\t");
                //System.out.print(rs.getString("name") + "\t");
                //System.out.println(rs.getString("menge") + "\t");
                lagerListe.add(new Lager(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return lagerListe;
    }

    /*ObservableList<Product> getBelegungListe() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dionysos.informatik.hs-augsburg.de/db01";
            Connection conn = null;
            //conn = DriverManager.getConnection(url, "db01", "MenPfN9wzcZA");
            conn = DriverManager.getConnection(url, "andr_user", "androiduser");
            int prnr = 100;
            String sql = "select * from produkt order by prnr";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                //
                //System.out.print(rs.getInt(1) + " ");
                //System.out.print(rs.getString(2) + " ");
                //System.out.println(rs.getInt(3));
                //System.out.print(rs.getString("prnr") + "\t");
                //System.out.print(rs.getString("name") + "\t");
                //System.out.println(rs.getString("menge") + "\t");
                products.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        this.productListe = products;
        return products;
    }*/

}
