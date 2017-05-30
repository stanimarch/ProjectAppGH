package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
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
        else if (window == 3)
            primaryStage.setScene(scene3);
    }

    public ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        return products;
    }
}
