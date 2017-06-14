import datentypen.Belegung;
import datentypen.Lager;
import datentypen.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.*;

public class DBTabellenController extends Controller {

    @FXML
    Pane pane;
    @FXML
    Text text;
    @FXML
    Button akt;

    // а вот эти таблцы я бы оставил
    TableView<Product> tableProdukt = new TableView<>();
    TableView<Lager> tableLager = new TableView<>();
    TableView<Belegung> tableBelegung = new TableView<>();

    //в теории, если эти одну из этих 3 переменных изменить, то должно и таблица в layout изменится
    ObservableList<Product> productListe;
    ObservableList<Lager> lagerListe;
    ObservableList<Belegung> belegungListe;
    String[][] belegung;


    @FXML
    public void aktualisieren() {
        System.out.println("public void aktualisieren()");

        productListe = getProductListe();
        tableProdukt.setItems(productListe);


        lagerListe = getLagerListe();
        tableLager.setItems(lagerListe);

        aktualisierungBelegungListe();
        belegungListe = getBelegungListe();
        tableBelegung.setItems(belegungListe);
    }

    @FXML
    public void zeigTabelleProdukt() {
        System.out.println("public void zeigTabelleProdukt()");

        pane.getChildren().clear();
        aktualisieren();
        pane.getChildren().addAll(tableProdukt);
        pane.setMaxSize(500, 300);
        text.setText("Tabelle PRODUKT");

        if (akt.isVisible() == false)
            akt.setVisible(true);
    }

    @FXML
    public void zeigTabelleLager() {
        System.out.println("public void zeigTabelleLager()");

        pane.getChildren().clear();
        aktualisieren();
        pane.getChildren().addAll(tableLager);
        pane.setMaxSize(500, 300);
        text.setText("Tabelle LAGER");
        if (akt.isVisible() == false)
            akt.setVisible(true);
    }

    @FXML
    public void zeigTabelleBelegung() {
        System.out.println("public void zeigTabelleBelegung()");

        pane.getChildren().clear();
        aktualisieren();
        pane.getChildren().addAll(tableBelegung);
        pane.setMaxSize(500, 300);
        text.setText("Tabelle BELEGUNG");

        if (akt.isVisible() == false)
            akt.setVisible(true);
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
        tableProdukt.setMaxSize(510, 500);


        TableColumn<Lager, String> renrColumn = new TableColumn<>("REGALNUMMER");
        renrColumn.setMinWidth(150);
        renrColumn.setCellValueFactory(new PropertyValueFactory<>("renr"));

        TableColumn<Lager, String> maxPlatzColumn = new TableColumn<>("KAPAZITÄT");
        maxPlatzColumn.setMinWidth(200);
        maxPlatzColumn.setCellValueFactory(new PropertyValueFactory<>("maxPlatz"));

        TableColumn<Lager, String> platzFreiColumn = new TableColumn<>("NOCH FREI");
        platzFreiColumn.setMinWidth(150);
        platzFreiColumn.setCellValueFactory(new PropertyValueFactory<>("platzFrei"));

        tableLager.setItems(getLagerListe());
        tableLager.getColumns().addAll(renrColumn, maxPlatzColumn, platzFreiColumn);
        tableLager.setMaxSize(510, 500);


        TableColumn<Belegung, String> ebeneColumn = new TableColumn<>("FACHNR");
        ebeneColumn.setMinWidth(50);
        ebeneColumn.setCellValueFactory(new PropertyValueFactory<>("ebene"));

        TableColumn<Belegung, String> regal1Column = new TableColumn<>("REGAL 1");
        regal1Column.setMinWidth(100);
        regal1Column.setCellValueFactory(new PropertyValueFactory<>("regal1"));

        TableColumn<Belegung, String> regal2Column = new TableColumn<>("REGAL 2");
        regal2Column.setMinWidth(100);
        regal2Column.setCellValueFactory(new PropertyValueFactory<>("regal2"));

        TableColumn<Belegung, String> regal3Column = new TableColumn<>("REGAL 3");
        regal3Column.setMinWidth(100);
        regal3Column.setCellValueFactory(new PropertyValueFactory<>("regal3"));

        TableColumn<Belegung, String> regal4Column = new TableColumn<>("REGAL 4");
        regal4Column.setMinWidth(100);
        regal4Column.setCellValueFactory(new PropertyValueFactory<>("regal4"));

        aktualisierungBelegungListe();
        tableBelegung.setItems(getBelegungListe());
        tableBelegung.getColumns().addAll(ebeneColumn, regal1Column, regal2Column, regal3Column, regal4Column);
        tableBelegung.setMaxSize(510, 500);
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

    void aktualisierungBelegungListe() {
        belegung = new String[8][5];
        for (int i = 0; i < 8; i++)
            belegung[i][0] = (i + 1) + "";
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dionysos.informatik.hs-augsburg.de/db01";
            Connection conn = null;
            conn = DriverManager.getConnection(url, "andr_user", "androiduser");
            String sql = "select * from ProdLager order by ErstDat";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                belegung[rs.getInt(3) - 1][Integer.parseInt("" + rs.getString(2).charAt(1))]
                        = "" + rs.getInt(1) + "\n(" + rs.getString(4) + ")";
                //
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    ObservableList<Belegung> getBelegungListe() {
        if (belegungListe == null)
            belegungListe = FXCollections.observableArrayList();
        else
            belegungListe.clear();

        for (int i = 0; i < belegung.length; i++) {
            belegungListe.add(new Belegung(belegung[i][0], belegung[i][1], belegung[i][2], belegung[i][3], belegung[i][4]));
        }
        return belegungListe;
    }

}
