import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Stage primaryStage;
    Scene scene1;
    Scene scene2;
    Scene scene3;

    // только для того, что бы в проблемном случае были всегда контроллеры под рукой в мейн методе
    // пока не ещё пригодились :D
    MainWindowController controller1;
    BarcodeEinscannenWindowController controller2;
    DBTabellenController controller3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Projekt-App");

        initAllScene();

        primaryStage.setMinHeight(400.0);
        primaryStage.setMinWidth(500.0);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private void initAllScene() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/MainWindow.fxml"));
            scene1 = new Scene(loader.load());

            controller1 = loader.getController();
            controller1.setMain(this);

            loader = new FXMLLoader(Main.class.getResource("fxml/BarcodeEinscannen.fxml"));
            scene2 = new Scene(loader.load());

            controller2 = loader.getController();
            controller2.setMain(this);

            loader = new FXMLLoader(Main.class.getResource("fxml/DBTabellen.fxml"));
            scene3 = new Scene(loader.load());

            controller3 = loader.getController();
            controller3.setMain(this);

            // для того, что бы все таблицы были уже готовы, и можно было и сними в DBTabellenController работать
            controller3.tabellenAnlegen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(int window) {
        if (window == 1)
            primaryStage.setScene(scene1);
        else if (window == 2)
            primaryStage.setScene(scene2);
        else if (window == 3) {
            controller3.aktualisieren();
            primaryStage.setScene(scene3);
        }
    }
}
