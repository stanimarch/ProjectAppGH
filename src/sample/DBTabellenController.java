package sample;


import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class DBTabellenController extends Controller {
    @FXML
    TableView<Product> table;

    @FXML @Override
    public void showTabellenWindow() {
        table = new TableView<>();
        main.changeScene(3);
    }
}
