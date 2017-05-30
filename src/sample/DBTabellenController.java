package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class DBTabellenController extends Controller {

    @FXML
    TableView table;
    TableView<Product> tableProdukt;
    //TableView<Lager> tableLager;
    //TableView<Belegung> tableBelegung;

    ObservableList<Product> products;
    //ObservableList<Lager> lager;
    //ObservableList<Belegung> lager;

    @FXML
    @Override
    public void showTabellenWindow() {
        table = new TableView<>();
        main.changeScene(3);
    }

    @FXML
    public void aktualisieren() {
    }

    void setTableProdukt() {
        table = tableProdukt;
    }

    void setTableLager() {
        table = null;
    }

    void setTableBelegung() {
        table = null;
    }

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

        tableProdukt.setItems(getProduct());
        tableProdukt.getColumns().addAll(idColumn, farbeColumn, mengeColumn);
        table = tableProdukt;


    }

    ObservableList<Product> getProduct() {
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
        this.products = products;
        return products;
    }

    ObservableList<Product> getLager() {
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
        this.products = products;
        return products;
    }

    ObservableList<Product> getBelegung() {
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
        this.products = products;
        return products;
    }
}
