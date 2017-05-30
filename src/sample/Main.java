package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Main extends Application {
    TableView<Product> table;
    private Stage primaryStage;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initAllScene();
        mainWindow();
    }

    private void initAllScene() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
            scene1 = new Scene(loader.load());

            MainWindowController controller1 = loader.getController();
            controller1.setMain(this);

            loader = new FXMLLoader(Main.class.getResource("BarcodeEinscannen.fxml"));
            scene2 = new Scene(loader.load());

            BarcodeEinscannenWindowController controller2 = loader.getController();
            controller2.setMain(this);

            loader = new FXMLLoader(Main.class.getResource("DBTabellen.fxml"));
            scene3 = new Scene(loader.load());

            DBTabellenController controller3 = loader.getController();
            controller3.setMain(this);
            table = controller3.table;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainWindow() {
        primaryStage.setMinHeight(400.0);
        primaryStage.setMinWidth(500.0);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }


    public void changeScene(int window) {
        if (window == 1)
            primaryStage.setScene(scene1);
        else if (window == 2)
            primaryStage.setScene(scene2);
        else if (window == 3) {
            tabelleAnlegen();
            primaryStage.setScene(scene3);
        }
    }

    public ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dionysos.informatik.hs-augsburg.de/db01";
            Connection conn = null;
            //conn = DriverManager.getConnection(url, "db01", "MenPfN9wzcZA");
            conn = DriverManager.getConnection(url, "andr_user", "androiduser");
            int prnr = 100;
            String sql = "SELECT * from produkt";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                /*System.out.print(rs.getInt(1) + " ");
                System.out.print(rs.getString(2) + " ");
                System.out.println(rs.getInt(3));*/
                //System.out.print(rs.getString("prnr") + "\t");
                //System.out.print(rs.getString("name") + "\t");
                //System.out.println(rs.getString("menge") + "\t");
                products.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    void tabelleAnlegen() {
        //Id column
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Farbe column
        TableColumn<Product, String> farbeColumn = new TableColumn<>("FARBE");
        farbeColumn.setMinWidth(100);
        farbeColumn.setCellValueFactory(new PropertyValueFactory<>("farbe"));

        //Id column
        TableColumn<Product, Integer> mengeColumn = new TableColumn<>("MENGE");
        mengeColumn.setMinWidth(100);
        mengeColumn.setCellValueFactory(new PropertyValueFactory<>("menge"));

        //table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(idColumn, farbeColumn, mengeColumn);
    }
}
