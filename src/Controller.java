import javafx.fxml.FXML;

public class Controller {
    public Main main;

    public void setMain(Main main) {
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
