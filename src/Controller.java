import javafx.fxml.FXML;


// Oberklasse für alle Controller
public class Controller {
    Main main;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void showMainWindow() {
        main.changeScene(1);
    }

    @FXML
    public void showBarcodeEinscannenWindow() {
        main.changeScene(2);
    }

    @FXML
    public void showTabellenWindow() {
        main.changeScene(3);
    }
}
